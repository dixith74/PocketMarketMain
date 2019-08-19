ALTER TABLE pm_order_prodcuts ADD COLUMN coupon BOOLEAN DEFAULT FALSE;
ALTER TABLE pm_order_prodcuts ADD COLUMN tax DOUBLE PRECISION;
ALTER TABLE pm_order_prodcuts ADD COLUMN discount DOUBLE PRECISION;
ALTER TABLE pm_order_prodcuts ADD COLUMN delivery_fee DOUBLE PRECISION;
ALTER TABLE pm_order_prodcuts ADD COLUMN delivery_addr character varying(50);