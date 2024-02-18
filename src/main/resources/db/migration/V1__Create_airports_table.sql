CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE airports
(
    airport_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code         VARCHAR(20) UNIQUE,
    name         VARCHAR(255) NOT NULL UNIQUE,
    country      VARCHAR(60)  NOT NULL
);