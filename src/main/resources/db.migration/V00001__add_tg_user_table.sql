-- ensure that the table with this name is removed before creating a new one.
DROP TABLE IF EXISTS dev_jrtb_db.tg_user;

-- Create tg_user table
CREATE TABLE dev_jrtb_db.tg_user (
                         chat_id VARCHAR(100),
                         active BOOLEAN
);