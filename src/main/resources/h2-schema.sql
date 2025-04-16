-- This is an empty query that does nothing
-- SELECT 1;
-- WordPress schema for H2 database

-- Drop existing tables if they exist
DROP TABLE IF EXISTS wp_commentmeta;
DROP TABLE IF EXISTS wp_comments;
DROP TABLE IF EXISTS wp_links;
DROP TABLE IF EXISTS wp_options;
DROP TABLE IF EXISTS wp_postmeta;
DROP TABLE IF EXISTS wp_posts;
DROP TABLE IF EXISTS wp_term_relationships;
DROP TABLE IF EXISTS wp_term_taxonomy;
DROP TABLE IF EXISTS wp_termmeta;
DROP TABLE IF EXISTS wp_terms;
DROP TABLE IF EXISTS wp_usermeta;
DROP TABLE IF EXISTS wp_users;

-- Table structure for wp_commentmeta
CREATE TABLE IF NOT EXISTS wp_commentmeta (
                                              meta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              comment_id BIGINT NOT NULL DEFAULT 0,
                                              meta_key VARCHAR(255) DEFAULT NULL,
    meta_value CLOB
    );
CREATE INDEX IF NOT EXISTS comment_id ON wp_commentmeta (comment_id);
CREATE INDEX IF NOT EXISTS meta_key ON wp_commentmeta (meta_key);

-- Table structure for wp_comments
CREATE TABLE IF NOT EXISTS wp_comments (
    comment_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_post_ID BIGINT NOT NULL DEFAULT 0,
    comment_author VARCHAR(255) NOT NULL,  -- Changed from CLOB to VARCHAR to mimic tinytext
    comment_author_email VARCHAR(100) NOT NULL DEFAULT '',
    comment_author_url VARCHAR(200) NOT NULL DEFAULT '',
    comment_author_IP VARCHAR(100) NOT NULL DEFAULT '',
    comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment_date_gmt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment_content CLOB NOT NULL,
    comment_karma INT NOT NULL DEFAULT 0,
    comment_approved VARCHAR(20) NOT NULL DEFAULT '1',
    comment_agent VARCHAR(255) NOT NULL DEFAULT '',
    comment_type VARCHAR(20) NOT NULL DEFAULT '',
    comment_parent BIGINT NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL DEFAULT 0
    );
CREATE INDEX IF NOT EXISTS comment_post_ID ON wp_comments (comment_post_ID);
CREATE INDEX IF NOT EXISTS comment_approved_date_gmt ON wp_comments (comment_approved, comment_date_gmt);
CREATE INDEX IF NOT EXISTS comment_date_gmt ON wp_comments (comment_date_gmt);
CREATE INDEX IF NOT EXISTS comment_parent ON wp_comments (comment_parent);
CREATE INDEX IF NOT EXISTS comment_author_email ON wp_comments (comment_author_email);

-- Table structure for wp_links
CREATE TABLE IF NOT EXISTS wp_links (
                                        link_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        link_url VARCHAR(255) NOT NULL DEFAULT '',
    link_name VARCHAR(255) NOT NULL DEFAULT '',
    link_image VARCHAR(255) NOT NULL DEFAULT '',
    link_target VARCHAR(25) NOT NULL DEFAULT '',
    link_description VARCHAR(255) NOT NULL DEFAULT '',
    link_visible VARCHAR(20) NOT NULL DEFAULT 'Y',
    link_owner BIGINT NOT NULL DEFAULT 1,
    link_rating INT NOT NULL DEFAULT 0,
    link_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    link_rel VARCHAR(255) NOT NULL DEFAULT '',
    link_notes CLOB NOT NULL,
    link_rss VARCHAR(255) NOT NULL DEFAULT ''
    );
CREATE INDEX IF NOT EXISTS link_visible ON wp_links (link_visible);

-- Table structure for wp_options
CREATE TABLE IF NOT EXISTS wp_options (
                                          option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          option_name VARCHAR(191) NOT NULL DEFAULT '',
    option_value CLOB NOT NULL,
    autoload VARCHAR(20) NOT NULL DEFAULT 'yes'
    );
CREATE UNIQUE INDEX IF NOT EXISTS option_name ON wp_options (option_name);

-- Table structure for wp_postmeta
CREATE TABLE IF NOT EXISTS wp_postmeta (
                                           meta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           post_id BIGINT NOT NULL DEFAULT 0,
                                           meta_key VARCHAR(255) DEFAULT NULL,
    meta_value CLOB
    );
CREATE INDEX IF NOT EXISTS post_id ON wp_postmeta (post_id);
CREATE INDEX IF NOT EXISTS meta_key ON wp_postmeta (meta_key);

-- Table structure for wp_posts
CREATE TABLE IF NOT EXISTS wp_posts (
                                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        post_author BIGINT NOT NULL DEFAULT 0,
                                        post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        post_date_gmt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        post_content CLOB NOT NULL,
                                        post_title CLOB NOT NULL,
                                        post_excerpt CLOB NOT NULL,
                                        post_status VARCHAR(20) NOT NULL DEFAULT 'publish',
    comment_status VARCHAR(20) NOT NULL DEFAULT 'open',
    ping_status VARCHAR(20) NOT NULL DEFAULT 'open',
    post_password VARCHAR(255) NOT NULL DEFAULT '',
    post_name VARCHAR(200) NOT NULL DEFAULT '',
    to_ping CLOB NOT NULL,
    pinged CLOB NOT NULL,
    post_modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_modified_gmt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_content_filtered CLOB NOT NULL,
    post_parent BIGINT NOT NULL DEFAULT 0,
    guid VARCHAR(255) NOT NULL DEFAULT '',
    menu_order INT NOT NULL DEFAULT 0,
    post_type VARCHAR(20) NOT NULL DEFAULT 'post',
    post_mime_type VARCHAR(100) NOT NULL DEFAULT '',
    comment_count BIGINT NOT NULL DEFAULT 0
    );
CREATE INDEX IF NOT EXISTS post_name ON wp_posts (post_name);
CREATE INDEX IF NOT EXISTS type_status_date ON wp_posts (post_type, post_status, post_date, ID);
CREATE INDEX IF NOT EXISTS post_parent ON wp_posts (post_parent);
CREATE INDEX IF NOT EXISTS post_author ON wp_posts (post_author);

-- Table structure for wp_term_relationships
CREATE TABLE IF NOT EXISTS wp_term_relationships (
                                                     object_id BIGINT NOT NULL DEFAULT 0,
                                                     term_taxonomy_id BIGINT NOT NULL DEFAULT 0,
                                                     term_order INT NOT NULL DEFAULT 0,
                                                     PRIMARY KEY (object_id, term_taxonomy_id)
    );
CREATE INDEX IF NOT EXISTS term_taxonomy_id ON wp_term_relationships (term_taxonomy_id);

-- Table structure for wp_term_taxonomy
CREATE TABLE IF NOT EXISTS wp_term_taxonomy (
                                                term_taxonomy_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                term_id BIGINT NOT NULL DEFAULT 0,
                                                taxonomy VARCHAR(32) NOT NULL DEFAULT '',
    description CLOB NOT NULL,
    parent BIGINT NOT NULL DEFAULT 0,
    count BIGINT NOT NULL DEFAULT 0
    );
CREATE UNIQUE INDEX IF NOT EXISTS term_id_taxonomy ON wp_term_taxonomy (term_id, taxonomy);
CREATE INDEX IF NOT EXISTS taxonomy ON wp_term_taxonomy (taxonomy);

-- Table structure for wp_termmeta
CREATE TABLE IF NOT EXISTS wp_termmeta (
                                           meta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           term_id BIGINT NOT NULL DEFAULT 0,
                                           meta_key VARCHAR(255) DEFAULT NULL,
    meta_value CLOB
    );
CREATE INDEX IF NOT EXISTS term_id ON wp_termmeta (term_id);
CREATE INDEX IF NOT EXISTS meta_key ON wp_termmeta (meta_key);

-- Table structure for wp_terms
CREATE TABLE IF NOT EXISTS wp_terms (
                                        term_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(200) NOT NULL DEFAULT '',
    slug VARCHAR(200) NOT NULL DEFAULT '',
    term_group BIGINT NOT NULL DEFAULT 0
    );
CREATE INDEX IF NOT EXISTS slug ON wp_terms (slug);
CREATE INDEX IF NOT EXISTS name ON wp_terms (name);

-- Table structure for wp_usermeta
CREATE TABLE IF NOT EXISTS wp_usermeta (
                                           umeta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL DEFAULT 0,
                                           meta_key VARCHAR(255) DEFAULT NULL,
    meta_value CLOB
    );
CREATE INDEX IF NOT EXISTS user_id ON wp_usermeta (user_id);
CREATE INDEX IF NOT EXISTS meta_key ON wp_usermeta (meta_key);

-- Table structure for wp_users
CREATE TABLE IF NOT EXISTS wp_users (
                                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_login VARCHAR(60) NOT NULL DEFAULT '',
    user_pass VARCHAR(255) NOT NULL DEFAULT '',
    user_nicename VARCHAR(50) NOT NULL DEFAULT '',
    user_email VARCHAR(100) NOT NULL DEFAULT '',
    user_url VARCHAR(100) NOT NULL DEFAULT '',
    user_registered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_activation_key VARCHAR(255) NOT NULL DEFAULT '',
    user_status INT NOT NULL DEFAULT 0,
    display_name VARCHAR(250) NOT NULL DEFAULT ''
    );
CREATE INDEX IF NOT EXISTS user_login_key ON wp_users (user_login);
CREATE INDEX IF NOT EXISTS user_nicename ON wp_users (user_nicename);
CREATE INDEX IF NOT EXISTS user_email ON wp_users (user_email);