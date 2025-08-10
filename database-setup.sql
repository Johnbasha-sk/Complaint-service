-- Municipality Complaint Management System Database Setup

-- Create database
CREATE DATABASE IF NOT EXISTS municipal_base;
USE municipal_base;

-- Create complaints table
CREATE TABLE IF NOT EXISTS complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    assigned_department VARCHAR(100),
    created_by BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_created_by (created_by),
    INDEX idx_status (status),
    INDEX idx_category (category),
    INDEX idx_assigned_department (assigned_department),
    INDEX idx_created_date (created_date)
);

-- Create comments table
CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    complaint_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    comment_text VARCHAR(1000) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (complaint_id) REFERENCES complaints(id) ON DELETE CASCADE,
    INDEX idx_complaint_id (complaint_id),
    INDEX idx_user_id (user_id),
    INDEX idx_timestamp (timestamp)
);

-- Insert sample data for testing
INSERT INTO complaints (category, description, status, created_by) VALUES
('WATER', 'Water pipe burst on Main Street', 'PENDING', 1),
('ROADS', 'Pothole on Highway 101', 'IN_PROGRESS', 2),
('SANITATION', 'Garbage collection missed for 3 days', 'PENDING', 1),
('WATER', 'Low water pressure in residential area', 'RESOLVED', 3);

INSERT INTO comments (complaint_id, user_id, role, comment_text) VALUES
(1, 1, 'CITIZEN', 'This is causing major traffic disruption'),
(1, 4, 'STAFF', 'We have dispatched a team to fix this issue'),
(2, 5, 'STAFF', 'Road maintenance crew assigned to this location'),
(3, 1, 'CITIZEN', 'The smell is getting worse each day');

COMMIT;