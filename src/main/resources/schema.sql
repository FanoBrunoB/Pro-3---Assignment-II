CREATE TABLE IF NOT EXISTS supplier (
    name VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS part_type (
     name VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS cow (
    id BIGSERIAL PRIMARY KEY,
    weight DOUBLE PRECISION NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    supplier_name VARCHAR(100) NOT NULL,
    CONSTRAINT fk_cow_supplier FOREIGN KEY (supplier_name) REFERENCES supplier (name)
);

CREATE TABLE IF NOT EXISTS cut_part (
    id BIGSERIAL PRIMARY KEY,
    part_type VARCHAR(100) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    cow BIGINT NOT NULL,
    CONSTRAINT fk_cut_part_part_type FOREIGN KEY (part_type) REFERENCES part_type (name),
    CONSTRAINT fk_cut_part_cow FOREIGN KEY (cow) REFERENCES cow (id)
);

CREATE TABLE IF NOT EXISTS distribution_product (
    id BIGSERIAL PRIMARY KEY,
    is_half BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS distribution_product_cut_part (
    distribution_product_id BIGINT NOT NULL,
    cut_part_id BIGINT NOT NULL,
    PRIMARY KEY (distribution_product_id, cut_part_id),
    CONSTRAINT fk_dpcp_product FOREIGN KEY (distribution_product_id) REFERENCES distribution_product (id),
    CONSTRAINT fk_dpcp_cut_part FOREIGN KEY (cut_part_id) REFERENCES cut_part (id)
);

CREATE TABLE IF NOT EXISTS tray (
    id BIGSERIAL PRIMARY KEY,
    max_weight DOUBLE PRECISION NOT NULL,
    cut_part BIGINT NOT NULL,
    CONSTRAINT fk_tray_cut_part FOREIGN KEY (cut_part) REFERENCES cut_part (id)
);

CREATE INDEX IF NOT EXISTS idx_cow_supplier ON cow (supplier_name);
CREATE INDEX IF NOT EXISTS idx_cut_part_cow ON cut_part (cow);
CREATE INDEX IF NOT EXISTS idx_dpcp_cut_part ON distribution_product_cut_part (cut_part_id);
CREATE INDEX IF NOT EXISTS idx_tray_cut_part ON tray (cut_part);