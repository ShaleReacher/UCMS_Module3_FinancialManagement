CREATE TABLE IF NOT EXISTS fees (
                                    fee_id SERIAL PRIMARY KEY,
                                    fee_type VARCHAR(100) NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(50),
    created_date DATE
    );

CREATE TABLE IF NOT EXISTS payments (
                                        payment_id SERIAL PRIMARY KEY,
                                        fee_id INT NOT NULL,
                                        amount NUMERIC(12, 2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(100),
    transaction_id VARCHAR(100),
    status VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            transaction_id SERIAL PRIMARY KEY,
                                            payment_id INT NOT NULL,
                                            transaction_type VARCHAR(50),
    amount NUMERIC(12, 2) NOT NULL,
    description VARCHAR(255),
    transaction_date DATE NOT NULL,
    status VARCHAR(50)
    );