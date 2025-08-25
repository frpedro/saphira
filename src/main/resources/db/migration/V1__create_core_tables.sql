CREATE TABLE investor (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    perfil_investidor VARCHAR(20) NOT NULL CHECK (perfil_investidor IN ('conservador', 'moderado', 'arrojado'))
);

CREATE TABLE investment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    valor_aplicado NUMERIC(15,2) NOT NULL,
    data_aplicacao DATE NOT NULL,
    ativo VARCHAR(100) NOT NULL,
    investidor_id UUID NOT NULL,
    CONSTRAINT fk_investidor FOREIGN KEY (investidor_id) REFERENCES investor(id) ON DELETE CASCADE
);
