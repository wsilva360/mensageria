package br.gov.sp.iamspe.mensageria.services.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.gov.sp.iamspe.mensageria.dto.EmailDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.FieldMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailValidator implements ConstraintValidator<EmailValidation, EmailDTO> {
    final private Pattern emailPattern = Pattern.compile(
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

    @Override
    public boolean isValid(EmailDTO dto, ConstraintValidatorContext context) {

        log.debug("[EmailValidator][isValid]", dto);

        List<FieldMessage> list = new ArrayList<>();

        //VALIDA ANEXOS
        if (dto.getAnexoList().size() > 0) {

            List<String> mediaList = Arrays.asList("application/pdf", "image/jpg", "image/jpeg", "image/png");

            dto.getAnexoList().forEach((anexo) -> {
                if (!mediaList.contains(this.extractMimeType(anexo.getArquivo()))) {
                    list.add(new FieldMessage("anexo", "Arquivos permitidos:" + mediaList));
                }
            });
        }

        //VALIDA CONTEUDO E MENSAGEM
        if (StringUtils.isBlank(dto.getMensagem()) && dto.getConteudo().isEmpty()) {
            list.add(new FieldMessage("mensagem", "Mensagem não pode ser nulo se conteudo tambem for nulo"));
            list.add(new FieldMessage("conteudo", "Conteudo não pode ser nulo se mensagem tambem for nulo"));
        }

        if (!dto.getConteudo().isEmpty() && StringUtils.isBlank(dto.getTemplate())) {
            list.add(new FieldMessage("template", "Template não pode ser nulo se houver conteudo."));
        }

        if (StringUtils.isNotBlank(dto.getMensagem()) && !dto.getConteudo().isEmpty()) {
            list.add(new FieldMessage("conteudo", "Informar somente conteudo ou mensagem."));
            list.add(new FieldMessage("mensagem", "Informar somente mensagem ou conteudo."));
        }

        //VALIDA PARA
        if (this.retornaEmailsInvalidos(dto.getPara()).count() > 0){
            this.retornaEmailsInvalidos(dto.getPara()).forEach(e -> { 
                list.add(new FieldMessage("para", "E-mail " + e + " inválido."));
            });
        }

        //VALIDA CC
        if (!dto.getCc().isEmpty() && this.retornaEmailsInvalidos(dto.getCc()).count() > 0){
            this.retornaEmailsInvalidos(dto.getCc()).forEach(e -> { 
                list.add(new FieldMessage("Cc", "E-mail " + e + " inválido."));
            });
        }

        //VALIDA CCO
        if (!dto.getCco().isEmpty() && this.retornaEmailsInvalidos(dto.getCco()).count() > 0){
            this.retornaEmailsInvalidos(dto.getCco()).forEach(e -> { 
                list.add(new FieldMessage("Cco", "E-mail " + e + " inválido."));
            });
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNome())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

    private String extractMimeType(final String encoded) {
        final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
        final Matcher matcher = mime.matcher(encoded);
        if (!matcher.find())
            return "";
        return matcher.group(1).toLowerCase();
    }

    private Stream<String> retornaEmailsInvalidos(List<String> list){
        return list.stream().filter(e -> StringUtils.isNotBlank(e)).filter(e -> !emailPattern.matcher(e).matches());
    }
}
