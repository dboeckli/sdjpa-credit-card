-- This is an empty query that does nothing
-- SELECT 1;
-- WordPress initial data for H2 database

-- Inserting data for wp_comments
INSERT INTO wp_comments (comment_ID, comment_post_ID, comment_author, comment_author_email, comment_author_url, comment_author_IP, comment_date, comment_date_gmt, comment_content, comment_karma, comment_approved, comment_agent, comment_type, comment_parent, user_id)
VALUES (1, 1, 'A WordPress Commenter', 'wapuu@wordpress.example', 'https://wordpress.org/', '', '2017-02-17 18:22:27', '2017-02-17 18:22:27', 'Hi, this is a comment.\nTo get started with moderating, editing, and deleting comments, please visit the Comments screen in the dashboard.\nCommenter avatars come from <a href="https://gravatar.com">Gravatar</a>.', 0, '1', '', '', 0, 0);

-- Inserting data for wp_options
INSERT INTO wp_options (option_name, option_value, autoload) VALUES
                                                                 ('siteurl', 'http://example.dev', 'yes'),
                                                                 ('home', 'http://example.dev', 'yes'),
                                                                 ('blogname', 'Welcome to the VCCW', 'yes'),
                                                                 ('blogdescription', 'Hello VCCW.', 'yes'),
                                                                 ('users_can_register', '0', 'yes'),
                                                                 ('admin_email', 'vccw@example.com', 'yes'),
                                                                 ('start_of_week', '1', 'yes'),
                                                                 ('use_balanceTags', '0', 'yes'),
                                                                 ('use_smilies', '1', 'yes'),
                                                                 ('require_name_email', '1', 'yes'),
                                                                 ('comments_notify', '1', 'yes'),
                                                                 ('posts_per_rss', '10', 'yes'),
                                                                 ('rss_use_excerpt', '0', 'yes'),
                                                                 ('mailserver_url', 'mail.example.com', 'yes'),
                                                                 ('mailserver_login', 'login@example.com', 'yes'),
                                                                 ('mailserver_pass', 'password', 'yes'),
                                                                 ('mailserver_port', '110', 'yes'),
                                                                 ('default_category', '1', 'yes'),
                                                                 ('default_comment_status', 'open', 'yes'),
                                                                 ('default_ping_status', 'open', 'yes'),
                                                                 ('default_pingback_flag', '1', 'yes'),
                                                                 ('posts_per_page', '10', 'yes'),
                                                                 ('date_format', 'M jS Y', 'yes'),
                                                                 ('time_format', 'g:i a', 'yes'),
                                                                 ('links_updated_date_format', 'F j, Y g:i a', 'yes');

-- Inserting data for wp_posts
INSERT INTO wp_posts (ID, post_author, post_date, post_date_gmt, post_content, post_title, post_excerpt, post_status, comment_status, ping_status, post_password, post_name, to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered, post_parent, guid, menu_order, post_type, post_mime_type, comment_count)
VALUES
    (1, 1, '2017-02-17 18:22:27', '2017-02-17 18:22:27', 'Welcome to WordPress. This is your first post. Edit or delete it, then start writing!', 'Hello world!', '', 'publish', 'open', 'open', '', 'hello-world', '', '', '2017-02-17 18:22:27', '2017-02-17 18:22:27', '', 0, 'http://example.dev/?p=1', 0, 'post', '', 1),
    (2, 1, '2017-02-17 18:22:27', '2017-02-17 18:22:27', 'This is an example page. It''s different from a blog post because it will stay in one place and will show up in your site navigation (in most themes). Most people start with an About page that introduces them to potential site visitors. It might say something like this:

<blockquote>Hi there! I''m a bike messenger by day, aspiring actor by night, and this is my website. I live in Los Angeles, have a great dog named Jack, and I like pi&#241;a coladas. (And gettin'' caught in the rain.)</blockquote>

...or something like this:

<blockquote>The XYZ Doohickey Company was founded in 1971, and has been providing quality doohickeys to the public ever since. Located in Gotham City, XYZ employs over 2,000 people and does all kinds of awesome things for the Gotham community.</blockquote>

As a new WordPress user, you should go to <a href="http://example.dev/wp-admin/">your dashboard</a> to delete this page and create new pages for your content. Have fun!', 'Sample Page', '', 'publish', 'closed', 'open', '', 'sample-page', '', '', '2017-02-17 18:22:27', '2017-02-17 18:22:27', '', 0, 'http://example.dev/?page_id=2', 0, 'page', '', 0);

-- Inserting data for wp_term_relationships
INSERT INTO wp_term_relationships (object_id, term_taxonomy_id, term_order) VALUES (1, 1, 0);

-- Inserting data for wp_term_taxonomy
INSERT INTO wp_term_taxonomy (term_taxonomy_id, term_id, taxonomy, description, parent, count) VALUES (1, 1, 'category', '', 0, 1);

-- Inserting data for wp_terms
INSERT INTO wp_terms (term_id, name, slug, term_group) VALUES (1, 'Uncategorized', 'uncategorized', 0);

-- Inserting data for wp_usermeta
INSERT INTO wp_usermeta (user_id, meta_key, meta_value) VALUES
                                                            (1, 'nickname', 'admin'),
                                                            (1, 'first_name', ''),
                                                            (1, 'last_name', ''),
                                                            (1, 'description', ''),
                                                            (1, 'rich_editing', 'true'),
                                                            (1, 'comment_shortcuts', 'false'),
                                                            (1, 'admin_color', 'fresh'),
                                                            (1, 'use_ssl', '0'),
                                                            (1, 'show_admin_bar_front', 'true'),
                                                            (1, 'locale', ''),
                                                            (1, 'wp_capabilities', 'a:1:{s:13:"administrator";b:1;}'),
                                                            (1, 'wp_user_level', '10'),
                                                            (1, 'dismissed_wp_pointers', ''),
                                                            (1, 'show_welcome_panel', '1');

-- Inserting data for wp_users
INSERT INTO wp_users (ID, user_login, user_pass, user_nicename, user_email, user_url, user_registered, user_activation_key, user_status, display_name)
VALUES (1, 'admin', '$P$BW1DUZv98znoluv.IjLGRqzgZjdKz20', 'admin', 'vccw@example.com', '', '2017-02-17 18:22:27', '', 0, 'admin');

-- Inserting data for wp_postmeta
INSERT INTO wp_postmeta (post_id, meta_key, meta_value) VALUES (2, '_wp_page_template', 'default');