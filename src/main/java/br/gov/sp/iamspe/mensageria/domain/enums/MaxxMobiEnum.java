package br.gov.sp.iamspe.mensageria.domain.enums;

/**
 * MaxxMobiEnum
 */
public enum MaxxMobiEnum {
    PENDENTE("P"), // A aplicação ainda não pegou a mensagem.
    ENVIADO("EN"), // A mensagem foi enviada.
    ERRO("ER"), // Ocorreu alguém erro ao tentar enviar a mensagem.
    NAO_ENVIADO("NE"), // A mensagem não foi enviada, por falta de crédito ou número invalido.
    ENTREGUE("ET"), // Confirmação de entrega ao destinatário
    BLACKLIST("BL"), // O número faz parte de uma blacklist
    DUPLICADO("DU");// Mensagens para destinatários iguais no mesmo envio.

    private String status;

    MaxxMobiEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    
}