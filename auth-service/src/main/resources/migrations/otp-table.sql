CREATE TABLE otp (
    id VARCHAR(32) PRIMARY KEY,
    user_id VARCHAR(32) NOT NULL,
    otp VARCHAR(6) NOT NULL,
    status ENUM('UN_USED', 'USED', 'REVOKED') NOT NULL,
    reason VARCHAR(255) NOT NULL,
    expire_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ip_address VARCHAR(45) NOT NULL, -- Stores the IP address (IPv4 or IPv6)
    user_agent VARCHAR(255) NOT NULL, -- Stores the user agent

    -- Index for OTP and user_id
    INDEX idx_otp__otp_user (otp, user_id),

    -- Foreign key index (if user_id is a foreign key to users table)
    INDEX idx_otp__user_id (user_id)
);
