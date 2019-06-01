package br.com.draju.templateapi.data;

public enum IdDocumentType {
    CPF(1),
    RG(2),
    CNH(3),
    PASSAPORT(4);

    private int documentCode;

    IdDocumentType(int documentCode) {
    }

    public int documentCode() {
        return documentCode;
    }
}
