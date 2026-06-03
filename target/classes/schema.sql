CREATE TABLE IF NOT EXISTS fees (
                                    id SERIAL PRIMARY KEY,
                                    fee_type VARCHAR(100) NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(50),
    created_date DATE
    );

CREATE TABLE IF NOT EXISTS payments (
                                        id SERIAL PRIMARY KEY,
                                        payment_date DATE NOT NULL,
                                        amount NUMERIC(12, 2) NOT NULL,
    payment_method VARCHAR(100),
    status VARCHAR(50),
    student_id INT
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            id SERIAL PRIMARY KEY,
                                            payment_id INT NOT NULL,
                                            fee_id INT NOT NULL,
                                            transaction_date DATE NOT NULL,
                                            amount NUMERIC(12, 2) NOT NULL,
    transaction_type VARCHAR(50),
    status VARCHAR(50)
    );
