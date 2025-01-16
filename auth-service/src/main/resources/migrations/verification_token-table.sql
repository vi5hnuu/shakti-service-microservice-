CREATE TABLE verification_token (
    id VARCHAR(32) PRIMARY KEY,
    user_id VARCHAR(32) NOT NULL,
    token VARCHAR(255) NOT NULL,
    status ENUM('UN_USED', 'USED', 'REVOKED') NOT NULL,
    reason VARCHAR(255) NOT NULL,
    expire_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Index for OTP and user_id
    INDEX idx_token_user (token, user_id),

    -- Foreign key index (if user_id is a foreign key to users table)
    INDEX idx_token_user_id (user_id)
);
