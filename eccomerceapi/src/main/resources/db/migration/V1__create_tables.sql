-- ./eccomerceapi/src/main/resources/db/migration/V1__create_tables.sql

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_user_email ON users(email);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    stock INT,
    parent_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    vendor_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE INDEX idx_product_name ON products(name);
CREATE INDEX idx_product_slug ON products(slug);