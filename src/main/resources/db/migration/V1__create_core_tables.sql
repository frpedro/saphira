CREATE TABLE investor (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    investor_profile VARCHAR(20) NOT NULL CHECK (investor_profile IN ('conservador', 'moderado', 'arrojado'))
);

CREATE TABLE investment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applied_value NUMERIC(15,2) NOT NULL,
    application_date DATE NOT NULL,
    asset VARCHAR(100) NOT NULL,
    investor_id UUID NOT NULL,
    CONSTRAINT fk_investor FOREIGN KEY (investor_id) REFERENCES investor(id) ON DELETE CASCADE
);
