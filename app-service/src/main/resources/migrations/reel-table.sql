CREATE TABLE reel_category (
    id VARCHAR(32) PRIMARY KEY, -- Unique ID for the category
    name VARCHAR(50) NOT NULL UNIQUE      -- Name of the category (e.g., Bhajans, Aarti)
);

CREATE TABLE reels (
    id VARCHAR(32) PRIMARY KEY,               -- Unique ID for the reel
    video_url VARCHAR(255) NOT NULL,          -- CloudFront URL of the video
    thumbnail_url VARCHAR(255) DEFAULT NULL,  -- CloudFront URL of the thumbnail
    title VARCHAR(255) NOT NULL,          -- Title of the reel
    description VARCHAR(255) DEFAULT NULL,    -- Short description for the reel
    category_id VARCHAR(32) DEFAULT NULL,                       -- Foreign key referencing reel_category
    is_active BOOLEAN DEFAULT TRUE,           -- Active/inactive status for the reel
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Time of upload
    FOREIGN KEY (category_id) REFERENCES reel_category(id) ON DELETE SET NULL
);

INSERT INTO reels (id, video_url, title) VALUES
(CONCAT('SRL', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)), 'https://dcn39gthlx55v.cloudfront.net/shakti-reels/om krishnaya vasudevaye.mp4', 'om Krishnaye vasudevaye...'),
(CONCAT('SRL', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)), 'https://dcn39gthlx55v.cloudfront.net/shakti-reels/apni santan ko karm karna sikhye.mp4', 'apni santan ko karm karna sikhye');