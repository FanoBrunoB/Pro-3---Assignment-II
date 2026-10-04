INSERT INTO supplier (name) VALUES ('Farm Alpha'), ('Farm Beta') ON CONFLICT DO NOTHING;

INSERT INTO part_type (name) VALUES ('Ribeye'), ('Sirloin'), ('Tenderloin'), ('Brisket') ON CONFLICT DO NOTHING;

INSERT INTO cow (id, weight, arrival_date, supplier_name) VALUES
    (1, 520.5, '2026-09-01 08:00:00', 'Farm Alpha'),
    (2, 498.0, '2026-09-01 08:30:00', 'Farm Alpha'),
    (3, 610.2, '2026-09-02 09:00:00', 'Farm Beta'),
    (4, 575.8, '2026-09-02 09:30:00', 'Farm Beta'),
    (5, 540.0, '2026-09-03 10:00:00', 'Farm Alpha')
ON CONFLICT DO NOTHING;

INSERT INTO cut_part (id, part_type, weight, cow) VALUES
    (1, 'Ribeye', 12.5, 1),
    (2, 'Sirloin', 9.8, 1),
    (3, 'Ribeye', 11.9, 2),
    (4, 'Tenderloin', 4.2, 2),
    (5, 'Sirloin', 10.4, 3),
    (6, 'Brisket', 8.7, 3),
    (7, 'Tenderloin', 4.6, 4),
    (8, 'Brisket', 9.1, 5)
ON CONFLICT DO NOTHING;

INSERT INTO distribution_product (id, is_half) VALUES
    (1, true),
    (2, false),
    (3, false),
    (4, true)
ON CONFLICT DO NOTHING;

INSERT INTO distribution_product_cut_part (distribution_product_id, cut_part_id) VALUES
    (1, 1), (1, 3), (1, 5),
    (2, 2), (2, 4),
    (3, 6), (3, 7),
    (4, 7)
ON CONFLICT DO NOTHING;

INSERT INTO tray (id, max_weight, cut_part) VALUES
    (1, 15.0, 1),
    (2, 12.0, 5),
    (3, 6.0, 7)
ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('cow', 'id'), (SELECT MAX(id) FROM cow));
SELECT setval(pg_get_serial_sequence('cut_part', 'id'), (SELECT MAX(id) FROM cut_part));
SELECT setval(pg_get_serial_sequence('distribution_product', 'id'), (SELECT MAX(id) FROM distribution_product));
SELECT setval(pg_get_serial_sequence('tray', 'id'), (SELECT MAX(id) FROM tray));