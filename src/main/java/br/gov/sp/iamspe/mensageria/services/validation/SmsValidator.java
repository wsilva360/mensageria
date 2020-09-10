package br.gov.sp.iamspe.mensageria.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.gov.sp.iamspe.mensageria.dto.SmsDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.FieldMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsValidator implements ConstraintValidator<SmsValidation, SmsDTO> {

    @Override
    public boolean isValid(SmsDTO dto, ConstraintValidatorContext context) {

        log.debug("[SmsValidator][isValid]", dto);

        List<FieldMessage> list = new ArrayList<>();

        if (!dto.getAcao().toUpperCase().equalsIgnoreCase("SEND")
                && !dto.getAcao().toUpperCase().equalsIgnoreCase("CANCEL")) {
            list.add(new FieldMessage("acao", "Ação inválida"));
        }

        if (dto.getAcao().toUpperCase().equalsIgnoreCase("SEND")
                && dto.getRequisicao().stream().filter(e -> StringUtils.isNotBlank(e.getIdCliente())).count() < dto.getRequisicao().size()) {
            list.add(new FieldMessage("idCliente", "idCliente não poder ser nulo ou vazio"));
        }

        if (dto.getAcao().toUpperCase().equalsIgnoreCase("CANCEL")
                && (dto.getRequisicao().size() > 0 || dto.getAgendamento() != null)) {
            list.add(new FieldMessage("requisicao", "Para cancelamento enviar somente o numero do lote, idApi."));
        }

        if (dto.getAcao().toUpperCase().equalsIgnoreCase("SEND") && dto.getLote() != null) {
            list.add(new FieldMessage("lote", "Não fornecer numero de lote para envio de mensagem."));
        }

        if (dto.getAcao().toUpperCase().equalsIgnoreCase("SEND") && dto.getRequisicao().size() == 0) {
            list.add(new FieldMessage("lote",
                    "Ao menos um numero e mensagem devem ser informados pra envio de um sms."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNome())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

}
