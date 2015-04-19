--
-- PostgreSQL database dump
--

-- Started on 2007-06-11 14:11:44 EDT

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = verilion, pg_catalog;

ALTER TABLE ONLY verilion.item_shipping DROP CONSTRAINT shipping_id_fk;
ALTER TABLE ONLY verilion.admin_page_group_role DROP CONSTRAINT role_id_fk;
ALTER TABLE ONLY verilion.user_role DROP CONSTRAINT role_id_fk;
ALTER TABLE ONLY verilion.admin_page_role DROP CONSTRAINT role_id_fk;
ALTER TABLE ONLY verilion.page_role DROP CONSTRAINT role_id_fk;
ALTER TABLE ONLY verilion.polls_data DROP CONSTRAINT poll_id_fk;
ALTER TABLE ONLY verilion.polls_check DROP CONSTRAINT poll_id_fk;
ALTER TABLE ONLY verilion.category DROP CONSTRAINT parent_category_id_fk;
ALTER TABLE ONLY verilion.jsp_template_page DROP CONSTRAINT page_id;
ALTER TABLE ONLY verilion.page_group_detail DROP CONSTRAINT page_id;
ALTER TABLE ONLY verilion.page_history DROP CONSTRAINT page_history_page_id_fkey;
ALTER TABLE ONLY verilion.page_role DROP CONSTRAINT page_group_id_fk;
ALTER TABLE ONLY verilion.page_group_detail DROP CONSTRAINT page_group_id_fk;
ALTER TABLE ONLY verilion.orders_detail DROP CONSTRAINT order_id_fk;
ALTER TABLE ONLY verilion.news_ticker DROP CONSTRAINT news_id_fk;
ALTER TABLE ONLY verilion.menu_item DROP CONSTRAINT menu_item_page_id_fkey;
ALTER TABLE ONLY verilion.jsp_template_page DROP CONSTRAINT jsp_template_id;
ALTER TABLE ONLY verilion.item_shipping DROP CONSTRAINT item_id_fk;
ALTER TABLE ONLY verilion.item_attributes DROP CONSTRAINT item_id_fk;
ALTER TABLE ONLY verilion.item_category DROP CONSTRAINT item_id_fk;
ALTER TABLE ONLY verilion.item_images DROP CONSTRAINT item_id_fk;
ALTER TABLE ONLY verilion.item_special DROP CONSTRAINT item_id;
ALTER TABLE ONLY verilion.customer_messages DROP CONSTRAINT fk_to_customer_id;
ALTER TABLE ONLY verilion.archive_page DROP CONSTRAINT fk_template_id;
ALTER TABLE ONLY verilion.page DROP CONSTRAINT fk_template_id;
ALTER TABLE ONLY verilion.admin_page DROP CONSTRAINT fk_template_id;
ALTER TABLE ONLY verilion.system_messages_detail DROP CONSTRAINT fk_system_message_id;
ALTER TABLE ONLY verilion.page_detail DROP CONSTRAINT fk_page_id;
ALTER TABLE ONLY verilion.documents DROP CONSTRAINT fk_page_id;
ALTER TABLE ONLY verilion.news DROP CONSTRAINT fk_news_categories_id;
ALTER TABLE ONLY verilion.modules DROP CONSTRAINT fk_module_position_id;
ALTER TABLE ONLY verilion.menu_item_detail DROP CONSTRAINT fk_menu_item_id;
ALTER TABLE ONLY verilion.menu_item DROP CONSTRAINT fk_menu_id;
ALTER TABLE ONLY verilion.customer_messages DROP CONSTRAINT fk_from_user_id_fk;
ALTER TABLE ONLY verilion.mailing_lists DROP CONSTRAINT fk_customer_id;
ALTER TABLE ONLY verilion.customer_address_detail DROP CONSTRAINT fk_customer_id;
ALTER TABLE ONLY verilion.customer_phone_detail DROP CONSTRAINT fk_customer_id;
ALTER TABLE ONLY verilion.customer_email_detail DROP CONSTRAINT fk_customer_id;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT fk_ct_term_id;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT fk_ct_salutation_id;
ALTER TABLE ONLY verilion.customer_address_detail DROP CONSTRAINT fk_ct_province_state_id;
ALTER TABLE ONLY verilion.customer_phone_detail DROP CONSTRAINT fk_ct_phone_type_id;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT fk_ct_package_id;
ALTER TABLE ONLY verilion.menu DROP CONSTRAINT fk_ct_menu_type_id;
ALTER TABLE ONLY verilion.menu DROP CONSTRAINT fk_ct_menu_alignment_type_id;
ALTER TABLE ONLY verilion.system_messages_detail DROP CONSTRAINT fk_ct_language_id;
ALTER TABLE ONLY verilion.page_detail DROP CONSTRAINT fk_ct_language_id;
ALTER TABLE ONLY verilion.menu_item_detail DROP CONSTRAINT fk_ct_language_id;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT fk_ct_credit_card_id;
ALTER TABLE ONLY verilion.customer_address_detail DROP CONSTRAINT fk_ct_country_id;
ALTER TABLE ONLY verilion.customer_address_detail DROP CONSTRAINT fk_ct_address_type_id;
ALTER TABLE ONLY verilion.system_pages DROP CONSTRAINT fk_ct_access_level_id;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT fk_ct_access_level_id;
ALTER TABLE ONLY verilion.archive_page_entry DROP CONSTRAINT fk_archive_page_id;
ALTER TABLE ONLY verilion.admin_menu_item_detail DROP CONSTRAINT fk_admin_menu_items_id;
ALTER TABLE ONLY verilion.archive_page DROP CONSTRAINT fk_access_level_id;
ALTER TABLE ONLY verilion.faq DROP CONSTRAINT faq_categories_id_fk;
ALTER TABLE ONLY verilion.orders DROP CONSTRAINT customer_id_fk;
ALTER TABLE ONLY verilion.user_role DROP CONSTRAINT customer_id_fk;
ALTER TABLE ONLY verilion.tax DROP CONSTRAINT ct_province_state_id_fk;
ALTER TABLE ONLY verilion.orders DROP CONSTRAINT ct_province_state_fk;
ALTER TABLE ONLY verilion.orders DROP CONSTRAINT ct_country_id_fk;
ALTER TABLE ONLY verilion.tax DROP CONSTRAINT ct_country_id;
ALTER TABLE ONLY verilion.item_category DROP CONSTRAINT category_id;
ALTER TABLE ONLY verilion.banners DROP CONSTRAINT banner_size_fk;
ALTER TABLE ONLY verilion.banners DROP CONSTRAINT banner_pos_fk;
ALTER TABLE ONLY verilion.admin_page_group_role DROP CONSTRAINT admin_page_id_fk;
ALTER TABLE ONLY verilion.admin_page_group_detail DROP CONSTRAINT admin_page_id_fk;
ALTER TABLE ONLY verilion.admin_page_role DROP CONSTRAINT admin_page_group_id_fk;
ALTER TABLE ONLY verilion.admin_page_group_detail DROP CONSTRAINT admin_page_group_id;
ALTER TABLE ONLY verilion.admin_page_detail DROP CONSTRAINT "$2";
ALTER TABLE ONLY verilion.admin_menu_items DROP CONSTRAINT "$2";
ALTER TABLE ONLY verilion.admin_page_detail DROP CONSTRAINT "$1";
DROP INDEX verilion.turba_owner_idx;
DROP INDEX verilion.special_item_item_id_ndx;
DROP INDEX verilion.sort_order_ndx;
DROP INDEX verilion.page_ndx_ph;
DROP INDEX verilion.page_name_page_index;
DROP INDEX verilion.page_id_ndx;
DROP INDEX verilion.page_dt_ndx_ph;
DROP INDEX verilion.ndx_ct_address_type;
DROP INDEX verilion.ndx_class_objects;
DROP INDEX verilion.ndx_class_object;
DROP INDEX verilion.menu_item_menu_item_detail_index;
DROP INDEX verilion.menu_id_menu_item_index;
DROP INDEX verilion.item_images_item_id_ndx;
DROP INDEX verilion.item_category_item_id_ndx;
DROP INDEX verilion.item_category_category_id_ndx;
DROP INDEX verilion.item_attributes_item_id_ndx;
DROP INDEX verilion.dt_ndx_ph;
DROP INDEX verilion.documents_page_id_ndx;
DROP INDEX verilion.customer_id_images_index;
DROP INDEX verilion.customer_id_customer_phone_detail_index;
DROP INDEX verilion.customer_id_customer_email_detail_index;
DROP INDEX verilion.customer_email_customer_email_detail_index;
DROP INDEX verilion.customer_customer_last_name_index;
DROP INDEX verilion.customer_customer_id_index;
DROP INDEX verilion.customer_customer_first_name_index;
DROP INDEX verilion.customer_customer_access_level_index;
DROP INDEX verilion.customer_address_detail_id_index;
DROP INDEX verilion.customer_address_detail_customer_id_index;
DROP INDEX verilion.ct_access_leve_id_index;
DROP INDEX verilion.admin_menu_items_sort_order_ndx;
DROP INDEX verilion.admin_menu_items_id_ndx;
DROP INDEX verilion.admin_menu_item_ndx;
ALTER TABLE ONLY verilion.user_role DROP CONSTRAINT user_role_pk;
ALTER TABLE ONLY verilion.system_messages_detail DROP CONSTRAINT unq_system_message_detail_display_name;
ALTER TABLE ONLY verilion."role" DROP CONSTRAINT unq_role_name;
ALTER TABLE ONLY verilion.page DROP CONSTRAINT unq_page_name;
ALTER TABLE ONLY verilion.class_objects DROP CONSTRAINT uniq_class_object;
ALTER TABLE ONLY verilion.turba_objects DROP CONSTRAINT turba_objects_pkey;
ALTER TABLE ONLY verilion."template" DROP CONSTRAINT template_pkey;
ALTER TABLE ONLY verilion.tax DROP CONSTRAINT tax_id_pk;
ALTER TABLE ONLY verilion.system_pages DROP CONSTRAINT system_pages_pkey;
ALTER TABLE ONLY verilion.system_messages DROP CONSTRAINT system_messages_pkey;
ALTER TABLE ONLY verilion.system_messages_detail DROP CONSTRAINT system_messages_detail_pkey;
ALTER TABLE ONLY verilion.style DROP CONSTRAINT style_pkey;
ALTER TABLE ONLY verilion.pmc_stories_comments DROP CONSTRAINT story_comment_pk;
ALTER TABLE ONLY verilion.pmc_story_category DROP CONSTRAINT story_category_pk;
ALTER TABLE ONLY verilion.item_special DROP CONSTRAINT special_id_pk;
ALTER TABLE ONLY verilion.shipping_rate_method DROP CONSTRAINT shipping_rate_method_pk;
ALTER TABLE ONLY verilion.shipping DROP CONSTRAINT shipping_id_pk;
ALTER TABLE ONLY verilion.sessions DROP CONSTRAINT sessions_pk;
ALTER TABLE ONLY verilion."role" DROP CONSTRAINT role_id_pk;
ALTER TABLE ONLY verilion.quotes DROP CONSTRAINT quotes_pkey;
ALTER TABLE ONLY verilion.promotions DROP CONSTRAINT promotions_pk;
ALTER TABLE ONLY verilion.polls DROP CONSTRAINT polls_pk;
ALTER TABLE ONLY verilion.polls_check DROP CONSTRAINT polls_check_pk;
ALTER TABLE ONLY verilion.polls_data DROP CONSTRAINT poll_option_pk;
ALTER TABLE ONLY verilion.pmc_stories DROP CONSTRAINT pmc_story_pk;
ALTER TABLE ONLY verilion.class_objects DROP CONSTRAINT pk_class_objects;
ALTER TABLE ONLY verilion.page_role DROP CONSTRAINT page_role_id_pk;
ALTER TABLE ONLY verilion.page DROP CONSTRAINT page_pkey;
ALTER TABLE ONLY verilion.page_history DROP CONSTRAINT page_history_pk;
ALTER TABLE ONLY verilion.page_group DROP CONSTRAINT page_group_id_pk;
ALTER TABLE ONLY verilion.page_group_detail DROP CONSTRAINT page_group_detail_pk;
ALTER TABLE ONLY verilion.page_detail DROP CONSTRAINT page_detail_pkey;
ALTER TABLE ONLY verilion.orders DROP CONSTRAINT order_id_pk;
ALTER TABLE ONLY verilion.orders_detail DROP CONSTRAINT order_details_id_pk;
ALTER TABLE ONLY verilion.news_ticker DROP CONSTRAINT news_ticker_pk;
ALTER TABLE ONLY verilion.news DROP CONSTRAINT news_pkey;
ALTER TABLE ONLY verilion.news_categories DROP CONSTRAINT news_categories_pkey;
ALTER TABLE ONLY verilion.modules DROP CONSTRAINT modules_pkey;
ALTER TABLE ONLY verilion.module_position DROP CONSTRAINT module_position_pkey;
ALTER TABLE ONLY verilion.message_queue DROP CONSTRAINT message_queue_pk;
ALTER TABLE ONLY verilion.menu DROP CONSTRAINT menu_pkey;
ALTER TABLE ONLY verilion.menu_item DROP CONSTRAINT menu_item_pkey;
ALTER TABLE ONLY verilion.menu_item_detail DROP CONSTRAINT menu_item_detail_pkey;
ALTER TABLE ONLY verilion.mailing_lists DROP CONSTRAINT mailing_lists_pkey;
ALTER TABLE ONLY verilion.logged_in_users DROP CONSTRAINT liupk;
ALTER TABLE ONLY verilion.links_categories DROP CONSTRAINT links_cat_pk;
ALTER TABLE ONLY verilion.links DROP CONSTRAINT link_pk;
ALTER TABLE ONLY verilion.jsp_templates DROP CONSTRAINT jsp_template_pk;
ALTER TABLE ONLY verilion.jsp_template_page DROP CONSTRAINT jsp_template_page_pk;
ALTER TABLE ONLY verilion.jsp_templates DROP CONSTRAINT jsp_template_name;
ALTER TABLE ONLY verilion.item_category DROP CONSTRAINT itemcategory_id_pk;
ALTER TABLE ONLY verilion.item_shipping DROP CONSTRAINT item_shipping_id_pk;
ALTER TABLE ONLY verilion.item_images DROP CONSTRAINT item_images_pkey;
ALTER TABLE ONLY verilion.item DROP CONSTRAINT item_id_pk;
ALTER TABLE ONLY verilion.item_attributes DROP CONSTRAINT item_attributes_id_pk;
ALTER TABLE ONLY verilion.images DROP CONSTRAINT images_pkey;
ALTER TABLE ONLY verilion.faq DROP CONSTRAINT faq_pk;
ALTER TABLE ONLY verilion.faq_categories DROP CONSTRAINT faq_categories_pk;
ALTER TABLE ONLY verilion.events DROP CONSTRAINT event_pk;
ALTER TABLE ONLY verilion.errors DROP CONSTRAINT errors_pkey;
ALTER TABLE ONLY verilion.documents DROP CONSTRAINT documents_pkey;
ALTER TABLE ONLY verilion.customer DROP CONSTRAINT customer_pkey;
ALTER TABLE ONLY verilion.customer_phone_detail DROP CONSTRAINT customer_phone_detail_pkey;
ALTER TABLE ONLY verilion.customer_messages_sent DROP CONSTRAINT customer_message_sent_pk;
ALTER TABLE ONLY verilion.customer_messages DROP CONSTRAINT customer_message_pk;
ALTER TABLE ONLY verilion.customer_email_detail DROP CONSTRAINT customer_email_detail_pkey;
ALTER TABLE ONLY verilion.customer_address_detail DROP CONSTRAINT customer_address_detail_pkey;
ALTER TABLE ONLY verilion.currency DROP CONSTRAINT currency_id_pk;
ALTER TABLE ONLY verilion.ct_term DROP CONSTRAINT ct_term_pkey;
ALTER TABLE ONLY verilion.ct_salutation DROP CONSTRAINT ct_salutation_pkey;
ALTER TABLE ONLY verilion.ct_province_state DROP CONSTRAINT ct_province_state_pkey;
ALTER TABLE ONLY verilion.ct_phone_type DROP CONSTRAINT ct_phone_type_pkey;
ALTER TABLE ONLY verilion.ct_packages DROP CONSTRAINT ct_packages_pkey;
ALTER TABLE ONLY verilion.ct_month DROP CONSTRAINT ct_month_pkey;
ALTER TABLE ONLY verilion.ct_menu_types DROP CONSTRAINT ct_menu_types_pkey;
ALTER TABLE ONLY verilion.ct_menu_alignment_types DROP CONSTRAINT ct_menu_alignment_types_pkey;
ALTER TABLE ONLY verilion.ct_languages DROP CONSTRAINT ct_languages_pkey;
ALTER TABLE ONLY verilion.ct_credit_card DROP CONSTRAINT ct_credit_card_pkey;
ALTER TABLE ONLY verilion.ct_country DROP CONSTRAINT ct_country_pkey;
ALTER TABLE ONLY verilion.ct_banner_sizes DROP CONSTRAINT ct_banner_sizes_pk;
ALTER TABLE ONLY verilion.ct_banner_position DROP CONSTRAINT ct_banner_pos_pk;
ALTER TABLE ONLY verilion.ct_address_type DROP CONSTRAINT ct_address_type_pkey;
ALTER TABLE ONLY verilion.ct_access_level DROP CONSTRAINT ct_access_level_pkey;
ALTER TABLE ONLY verilion.components DROP CONSTRAINT components_pk;
ALTER TABLE ONLY verilion.modules DROP CONSTRAINT ck_module_class_name;
ALTER TABLE ONLY verilion.category DROP CONSTRAINT category_id_pk;
ALTER TABLE ONLY verilion.careers DROP CONSTRAINT careers_pk;
ALTER TABLE ONLY verilion.career_type DROP CONSTRAINT career_type_pk;
ALTER TABLE ONLY verilion.career_locations DROP CONSTRAINT career_location_pk;
ALTER TABLE ONLY verilion.banner_impressions DROP CONSTRAINT biidpk;
ALTER TABLE ONLY verilion.banner_clicks DROP CONSTRAINT bcidpk;
ALTER TABLE ONLY verilion.banners DROP CONSTRAINT banner_id_pk;
ALTER TABLE ONLY verilion.archive_page DROP CONSTRAINT archive_page_pkey;
ALTER TABLE ONLY verilion.archive_page_entry DROP CONSTRAINT archive_page_entry_pkey;
ALTER TABLE ONLY verilion.admin_page_role DROP CONSTRAINT admin_page_role_pk;
ALTER TABLE ONLY verilion.admin_page DROP CONSTRAINT admin_page_pkey;
ALTER TABLE ONLY verilion.admin_page_group_role DROP CONSTRAINT admin_page_group_role_id_pk;
ALTER TABLE ONLY verilion.admin_page_group DROP CONSTRAINT admin_page_group_id_pk;
ALTER TABLE ONLY verilion.admin_page_group_detail DROP CONSTRAINT admin_page_group_detail_pk;
ALTER TABLE ONLY verilion.admin_page_detail DROP CONSTRAINT admin_page_detail_pkey;
ALTER TABLE ONLY verilion.admin_menu DROP CONSTRAINT admin_menu_pkey;
ALTER TABLE ONLY verilion.admin_menu_items DROP CONSTRAINT admin_menu_items_pkey;
ALTER TABLE ONLY verilion.admin_menu_item_detail DROP CONSTRAINT admin_menu_item_detail_pkey;
ALTER TABLE verilion.user_role ALTER COLUMN user_role_id DROP DEFAULT;
ALTER TABLE verilion.tax ALTER COLUMN tax_id DROP DEFAULT;
ALTER TABLE verilion.shipping_rate_method ALTER COLUMN shipping_rate_method_id DROP DEFAULT;
ALTER TABLE verilion.shipping ALTER COLUMN shipping_id DROP DEFAULT;
ALTER TABLE verilion.sessions ALTER COLUMN session_id DROP DEFAULT;
ALTER TABLE verilion.servers ALTER COLUMN server_id DROP DEFAULT;
ALTER TABLE verilion.promotions ALTER COLUMN promotion_id DROP DEFAULT;
ALTER TABLE verilion.polls_data ALTER COLUMN polls_data_id DROP DEFAULT;
ALTER TABLE verilion.polls_check ALTER COLUMN polls_check_id DROP DEFAULT;
ALTER TABLE verilion.polls ALTER COLUMN poll_id DROP DEFAULT;
ALTER TABLE verilion.pmc_story_category ALTER COLUMN story_category_id DROP DEFAULT;
ALTER TABLE verilion.pmc_stories_comments ALTER COLUMN comment_id DROP DEFAULT;
ALTER TABLE verilion.pmc_stories ALTER COLUMN story_id DROP DEFAULT;
ALTER TABLE verilion.page_role ALTER COLUMN page_role_id DROP DEFAULT;
ALTER TABLE verilion.page_history ALTER COLUMN page_history_id DROP DEFAULT;
ALTER TABLE verilion.page_group_detail ALTER COLUMN page_group_detail_id DROP DEFAULT;
ALTER TABLE verilion.page_group ALTER COLUMN page_group_id DROP DEFAULT;
ALTER TABLE verilion.orders_detail ALTER COLUMN orders_detail_id DROP DEFAULT;
ALTER TABLE verilion.orders ALTER COLUMN order_id DROP DEFAULT;
ALTER TABLE verilion.news_ticker ALTER COLUMN news_ticker_id DROP DEFAULT;
ALTER TABLE verilion.message_queue ALTER COLUMN message_queue_id DROP DEFAULT;
ALTER TABLE verilion.logged_in_users ALTER COLUMN logged_in_user_id DROP DEFAULT;
ALTER TABLE verilion.links_categories ALTER COLUMN links_cat_id DROP DEFAULT;
ALTER TABLE verilion.links ALTER COLUMN link_id DROP DEFAULT;
ALTER TABLE verilion.jsp_templates ALTER COLUMN jsp_template_id DROP DEFAULT;
ALTER TABLE verilion.jsp_template_page ALTER COLUMN jsp_template_page_id DROP DEFAULT;
ALTER TABLE verilion.item_special ALTER COLUMN item_special_id DROP DEFAULT;
ALTER TABLE verilion.item_shipping ALTER COLUMN item_shipping_id DROP DEFAULT;
ALTER TABLE verilion.item_images ALTER COLUMN item_image_id DROP DEFAULT;
ALTER TABLE verilion.item_category ALTER COLUMN item_category_id DROP DEFAULT;
ALTER TABLE verilion.item_attributes ALTER COLUMN item_attributes_id DROP DEFAULT;
ALTER TABLE verilion.item ALTER COLUMN item_id DROP DEFAULT;
ALTER TABLE verilion.faq_categories ALTER COLUMN faq_categories_id DROP DEFAULT;
ALTER TABLE verilion.faq ALTER COLUMN faq_id DROP DEFAULT;
ALTER TABLE verilion.events ALTER COLUMN event_id DROP DEFAULT;
ALTER TABLE verilion.customer_messages_sent ALTER COLUMN customer_message_sent_id DROP DEFAULT;
ALTER TABLE verilion.customer_messages ALTER COLUMN customer_message_id DROP DEFAULT;
ALTER TABLE verilion.currency ALTER COLUMN currency_id DROP DEFAULT;
ALTER TABLE verilion.ct_banner_sizes ALTER COLUMN ct_banner_sizes_id DROP DEFAULT;
ALTER TABLE verilion.ct_banner_position ALTER COLUMN ct_banner_position_id DROP DEFAULT;
ALTER TABLE verilion.components ALTER COLUMN component_id DROP DEFAULT;
ALTER TABLE verilion.category ALTER COLUMN category_id DROP DEFAULT;
ALTER TABLE verilion.careers ALTER COLUMN career_id DROP DEFAULT;
ALTER TABLE verilion.career_type ALTER COLUMN career_type_id DROP DEFAULT;
ALTER TABLE verilion.career_locations ALTER COLUMN career_locations_id DROP DEFAULT;
ALTER TABLE verilion.banners ALTER COLUMN banner_id DROP DEFAULT;
ALTER TABLE verilion.banner_impressions ALTER COLUMN banner_impressions_id DROP DEFAULT;
ALTER TABLE verilion.banner_clicks ALTER COLUMN banner_clicks_id DROP DEFAULT;
ALTER TABLE verilion.admin_page_role ALTER COLUMN admin_page_role_id DROP DEFAULT;
ALTER TABLE verilion.admin_page_group_role ALTER COLUMN admin_page_group_role_id DROP DEFAULT;
ALTER TABLE verilion.admin_page_group_detail ALTER COLUMN admin_page_group_detail_id DROP DEFAULT;
ALTER TABLE verilion.admin_page_group ALTER COLUMN admin_page_group_id DROP DEFAULT;
DROP VIEW verilion.v_user_roles;
DROP VIEW verilion.v_province_state;
DROP VIEW verilion.v_customer;
DROP VIEW verilion.v_buyers;
DROP VIEW verilion.v_admins;
DROP SEQUENCE verilion.user_role_user_role_id_seq;
DROP TABLE verilion.user_role;
DROP TABLE verilion.turba_objects;
DROP SEQUENCE verilion.tree_menu_level_two_id_seq;
DROP TABLE verilion.tree_menu_level_two;
DROP SEQUENCE verilion.tree_menu_level_one_id_seq;
DROP TABLE verilion.tree_menu_level_one;
DROP SEQUENCE verilion.tree_menu_id_seq;
DROP TABLE verilion.tree_menu;
DROP SEQUENCE verilion.template_template_id_seq;
DROP TABLE verilion."template";
DROP SEQUENCE verilion.tax_tax_id_seq;
DROP TABLE verilion.tax;
DROP TABLE verilion.system_preferences;
DROP SEQUENCE verilion.system_pages_system_page_id_;
DROP TABLE verilion.system_pages;
DROP SEQUENCE verilion.system_messages_system_messa;
DROP SEQUENCE verilion.system_messages_detail_syste;
DROP TABLE verilion.system_messages_detail;
DROP TABLE verilion.system_messages;
DROP SEQUENCE verilion.style_style_id_seq;
DROP TABLE verilion.style;
DROP SEQUENCE verilion.species_id_seq;
DROP SEQUENCE verilion.sire_id_seq;
DROP SEQUENCE verilion.shipping_shipping_id_seq;
DROP SEQUENCE verilion.shipping_rate_method_shipping_rate_method_id_seq;
DROP TABLE verilion.shipping_rate_method;
DROP TABLE verilion.shipping;
DROP SEQUENCE verilion.sessions_session_id_seq;
DROP TABLE verilion.sessions;
DROP SEQUENCE verilion.servers_server_id_seq;
DROP TABLE verilion.servers;
DROP SEQUENCE verilion.role_id_seq;
DROP SEQUENCE verilion.role_assigned_id_seq;
DROP TABLE verilion."role";
DROP SEQUENCE verilion.quotes_seq;
DROP TABLE verilion.quotes;
DROP SEQUENCE verilion.promotions_promotion_id_seq;
DROP TABLE verilion.promotions;
DROP SEQUENCE verilion.polls_poll_id_seq;
DROP SEQUENCE verilion.polls_data_polls_data_id_seq;
DROP TABLE verilion.polls_data;
DROP SEQUENCE verilion.polls_check_polls_check_id_seq;
DROP TABLE verilion.polls_check;
DROP TABLE verilion.polls;
DROP SEQUENCE verilion.pmc_story_category_story_category_id_seq;
DROP TABLE verilion.pmc_story_category;
DROP SEQUENCE verilion.pmc_stories_story_id_seq;
DROP SEQUENCE verilion.pmc_stories_comments_comment_id_seq;
DROP TABLE verilion.pmc_stories_comments;
DROP TABLE verilion.pmc_stories;
DROP SEQUENCE verilion.page_role_page_role_id_seq;
DROP TABLE verilion.page_role;
DROP SEQUENCE verilion.page_page_id_seq;
DROP SEQUENCE verilion.page_history_page_history_id_seq;
DROP TABLE verilion.page_history;
DROP SEQUENCE verilion.page_group_page_group_id_seq;
DROP SEQUENCE verilion.page_group_detail_page_group_detail_id_seq;
DROP TABLE verilion.page_group_detail;
DROP TABLE verilion.page_group;
DROP SEQUENCE verilion.page_detail_page_detail_id_s;
DROP TABLE verilion.page_detail;
DROP TABLE verilion.page;
DROP SEQUENCE verilion.orders_order_id_seq;
DROP SEQUENCE verilion.orders_detail_orders_detail_id_seq;
DROP TABLE verilion.orders_detail;
DROP TABLE verilion.orders;
DROP SEQUENCE verilion.news_ticker_news_ticker_id_seq;
DROP TABLE verilion.news_ticker;
DROP SEQUENCE verilion.news_news_id_seq;
DROP SEQUENCE verilion.news_categories_id_seq;
DROP TABLE verilion.news_categories;
DROP TABLE verilion.news;
DROP SEQUENCE verilion.modules_module_id_seq;
DROP TABLE verilion.modules;
DROP SEQUENCE verilion.module_postion_module_position_id_seq;
DROP TABLE verilion.module_position;
DROP SEQUENCE verilion.message_queue_message_queue_id_seq;
DROP TABLE verilion.message_queue;
DROP SEQUENCE verilion.menu_menu_id_seq;
DROP SEQUENCE verilion.menu_item_menu_item_id_seq;
DROP SEQUENCE verilion.menu_item_detail_menu_item_d;
DROP TABLE verilion.menu_item_detail;
DROP TABLE verilion.menu_item;
DROP TABLE verilion.menu;
DROP SEQUENCE verilion.mailing_lists_list_id_seq;
DROP TABLE verilion.mailing_lists;
DROP SEQUENCE verilion.logged_in_users_logged_in_user_id_seq;
DROP TABLE verilion.logged_in_users;
DROP SEQUENCE verilion.litters_litter_id_seq;
DROP SEQUENCE verilion.links_link_id_seq;
DROP SEQUENCE verilion.links_categories_links_cat_id_seq;
DROP TABLE verilion.links_categories;
DROP TABLE verilion.links;
DROP SEQUENCE verilion.jsp_templates_jsp_template_id_seq;
DROP TABLE verilion.jsp_templates;
DROP SEQUENCE verilion.jsp_template_page_jsp_template_page_id_seq;
DROP TABLE verilion.jsp_template_page;
DROP SEQUENCE verilion.item_special_item_special_id_seq;
DROP TABLE verilion.item_special;
DROP SEQUENCE verilion.item_shipping_item_shipping_id_seq;
DROP TABLE verilion.item_shipping;
DROP SEQUENCE verilion.item_item_id_seq;
DROP SEQUENCE verilion.item_images_item_image_id_seq;
DROP TABLE verilion.item_images;
DROP SEQUENCE verilion.item_category_item_category_id_seq;
DROP TABLE verilion.item_category;
DROP SEQUENCE verilion.item_attributes_item_attributes_id_seq;
DROP TABLE verilion.item_attributes;
DROP TABLE verilion.item;
DROP SEQUENCE verilion.images_image_id_seq;
DROP TABLE verilion.images;
DROP SEQUENCE verilion.faq_faq_id_seq;
DROP SEQUENCE verilion.faq_categories_faq_categories_id_seq;
DROP TABLE verilion.faq_categories;
DROP TABLE verilion.faq;
DROP SEQUENCE verilion.events_event_id_seq;
DROP TABLE verilion.events;
DROP SEQUENCE verilion.errors_error_id_seq;
DROP TABLE verilion.errors;
DROP TABLE verilion.documents;
DROP SEQUENCE verilion.document_id_seq;
DROP SEQUENCE verilion.customer_phone_detail_custom;
DROP TABLE verilion.customer_phone_detail;
DROP SEQUENCE verilion.customer_messages_sent_customer_message_sent_id_seq;
DROP TABLE verilion.customer_messages_sent;
DROP SEQUENCE verilion.customer_messages_customer_message_id_seq;
DROP TABLE verilion.customer_messages;
DROP SEQUENCE verilion.customer_email_detail_custom;
DROP TABLE verilion.customer_email_detail;
DROP SEQUENCE verilion.customer_customer_id_seq;
DROP SEQUENCE verilion.customer_breed_customer_breed_id_seq;
DROP SEQUENCE verilion.customer_address_detail_cust;
DROP TABLE verilion.customer_address_detail;
DROP TABLE verilion.customer;
DROP SEQUENCE verilion.currency_currency_id_seq;
DROP TABLE verilion.currency;
DROP SEQUENCE verilion.ct_term_ct_term_id_seq;
DROP TABLE verilion.ct_term;
DROP SEQUENCE verilion.ct_salutation_ct_salutation_;
DROP TABLE verilion.ct_salutation;
DROP SEQUENCE verilion.ct_province_state_ct_provinc;
DROP TABLE verilion.ct_province_state;
DROP SEQUENCE verilion.ct_phone_type_ct_phone_type_;
DROP TABLE verilion.ct_phone_type;
DROP SEQUENCE verilion.ct_packages_ct_package_id_se;
DROP TABLE verilion.ct_packages;
DROP SEQUENCE verilion.ct_month_ct_month_id_seq;
DROP TABLE verilion.ct_month;
DROP SEQUENCE verilion.ct_menu_types_ct_menu_type_i;
DROP TABLE verilion.ct_menu_types;
DROP SEQUENCE verilion.ct_menu_alignment_types_ct_m;
DROP TABLE verilion.ct_menu_alignment_types;
DROP SEQUENCE verilion.ct_languages_ct_language_id_;
DROP TABLE verilion.ct_languages;
DROP SEQUENCE verilion.ct_credit_card_ct_credit_car;
DROP TABLE verilion.ct_credit_card;
DROP SEQUENCE verilion.ct_country_ct_country_id_seq;
DROP TABLE verilion.ct_country;
DROP SEQUENCE verilion.ct_banner_sizes_ct_banner_sizes_id_seq;
DROP TABLE verilion.ct_banner_sizes;
DROP SEQUENCE verilion.ct_banner_position_ct_banner_position_id_seq;
DROP TABLE verilion.ct_banner_position;
DROP SEQUENCE verilion.ct_address_type_ct_address_t;
DROP TABLE verilion.ct_address_type;
DROP SEQUENCE verilion.ct_access_level_ct_access_le;
DROP TABLE verilion.ct_access_level;
DROP SEQUENCE verilion.components_component_id_seq;
DROP TABLE verilion.components;
DROP SEQUENCE verilion.class_objects_id_seq;
DROP TABLE verilion.class_objects;
DROP SEQUENCE verilion.category_category_id_seq;
DROP TABLE verilion.category;
DROP SEQUENCE verilion.careers_career_id_seq;
DROP TABLE verilion.careers;
DROP SEQUENCE verilion.career_type_career_type_id_seq;
DROP TABLE verilion.career_type;
DROP SEQUENCE verilion.career_locations_career_locations_id_seq;
DROP TABLE verilion.career_locations;
DROP SEQUENCE verilion.breedclub_id_seq;
DROP SEQUENCE verilion.breed_id_seq;
DROP SEQUENCE verilion.banners_banner_id_seq;
DROP TABLE verilion.banners;
DROP SEQUENCE verilion.banner_impressions_banner_impressions_id_seq;
DROP TABLE verilion.banner_impressions;
DROP SEQUENCE verilion.banner_clicks_banner_clicks_id_seq;
DROP TABLE verilion.banner_clicks;
DROP SEQUENCE verilion.archive_page_id_seq;
DROP SEQUENCE verilion.archive_page_entry_id_seq;
DROP TABLE verilion.archive_page_entry;
DROP TABLE verilion.archive_page;
DROP SEQUENCE verilion.adult_id_seq;
DROP SEQUENCE verilion.admin_page_role_admin_page_role_id_seq;
DROP TABLE verilion.admin_page_role;
DROP SEQUENCE verilion.admin_page_page_id_seq;
DROP SEQUENCE verilion.admin_page_group_role_admin_page_group_role_id_seq;
DROP TABLE verilion.admin_page_group_role;
DROP SEQUENCE verilion.admin_page_group_detail_admin_page_group_detail_id_seq;
DROP TABLE verilion.admin_page_group_detail;
DROP SEQUENCE verilion.admin_page_group_admin_page_group_id_seq;
DROP TABLE verilion.admin_page_group;
DROP SEQUENCE verilion.admin_page_detail_page_detail_id_s;
DROP TABLE verilion.admin_page_detail;
DROP TABLE verilion.admin_page;
DROP SEQUENCE verilion.admin_menu_seq;
DROP SEQUENCE verilion.admin_menu_items_id_seq;
DROP SEQUENCE verilion.admin_menu_items_detail_seq;
DROP TABLE verilion.admin_menu_items;
DROP TABLE verilion.admin_menu_item_detail;
DROP TABLE verilion.admin_menu;
DROP SCHEMA verilion;
--
-- TOC entry 27 (class 2615 OID 16407)
-- Name: verilion; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA verilion;


SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 11734 (class 1259 OID 34133)
-- Dependencies: 13610 13611 13612 13613 13614 13615 13616 13617 13618 13619 13620 13621 13622 27
-- Name: admin_menu; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_menu (
    use_textlinks smallint DEFAULT 1 NOT NULL,
    start_all_open smallint DEFAULT 0 NOT NULL,
    use_icons smallint DEFAULT 1 NOT NULL,
    wrap_text smallint DEFAULT 1 NOT NULL,
    preserve_state smallint DEFAULT 1 NOT NULL,
    highlight smallint DEFAULT 1 NOT NULL,
    highlight_color character varying(255) DEFAULT 'white'::character varying NOT NULL,
    highlight_bg character varying(255) DEFAULT '#333333'::character varying NOT NULL,
    icon_path character varying(255) DEFAULT '/js/'::character varying NOT NULL,
    folders_tree_id character varying(255) DEFAULT 't2'::character varying NOT NULL,
    menu_title character varying(255) DEFAULT 'Administration'::character varying NOT NULL,
    main_uri text DEFAULT '/servlet/com.verilion.display.html.SecurePage?page=Administration'::text NOT NULL,
    admin_menu_id integer DEFAULT nextval(('verilion.admin_menu_seq'::text)::regclass) NOT NULL
);


--
-- TOC entry 11735 (class 1259 OID 34151)
-- Dependencies: 13623 13624 13625 13626 13627 13628 13629 27
-- Name: admin_menu_item_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_menu_item_detail (
    admin_menu_item_detail_id integer DEFAULT nextval(('verilion.admin_menu_items_detail_seq'::text)::regclass) NOT NULL,
    admin_menu_item_detail_target character(1) DEFAULT 'S'::bpchar NOT NULL,
    admin_menu_item_detail_name character varying(255) DEFAULT ''::character varying NOT NULL,
    admin_menu_item_detail_uri text DEFAULT 'javascript:undefined'::text NOT NULL,
    admin_menu_items_id integer,
    admin_menu_item_detail_order integer,
    admin_menu_item_detail_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    page_id integer DEFAULT 0,
    CONSTRAINT ck_admin_menu_item_detail_active_yn CHECK (((admin_menu_item_detail_active_yn = 'y'::bpchar) OR (admin_menu_item_detail_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11736 (class 1259 OID 34163)
-- Dependencies: 13630 13631 13632 13633 13634 13635 13636 27
-- Name: admin_menu_items; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_menu_items (
    admin_menu_items_id integer DEFAULT nextval(('verilion.admin_menu_items_id_seq'::text)::regclass) NOT NULL,
    admin_menu_items_name character varying(255) DEFAULT ''::character varying NOT NULL,
    admin_menu_items_uri text DEFAULT 'javascript:undefined'::text NOT NULL,
    admin_menu_items_order integer,
    admin_menu_id integer DEFAULT 1 NOT NULL,
    admin_menu_items_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    page_id integer DEFAULT 0,
    CONSTRAINT ck_admin_menu_items_active_yn CHECK (((admin_menu_items_active_yn = 'y'::bpchar) OR (admin_menu_items_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11737 (class 1259 OID 34175)
-- Dependencies: 27
-- Name: admin_menu_items_detail_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_menu_items_detail_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11738 (class 1259 OID 34177)
-- Dependencies: 27
-- Name: admin_menu_items_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_menu_items_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11739 (class 1259 OID 34179)
-- Dependencies: 27
-- Name: admin_menu_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_menu_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11740 (class 1259 OID 34181)
-- Dependencies: 13637 13638 13639 13640 13641 13642 13643 13644 13645 13646 13647 27
-- Name: admin_page; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page (
    page_id integer DEFAULT nextval(('verilion.admin_page_page_id_seq'::text)::regclass) NOT NULL,
    page_name character varying(255) DEFAULT ''::character varying NOT NULL,
    template_id integer DEFAULT 0 NOT NULL,
    page_access_level smallint DEFAULT 0 NOT NULL,
    page_invocation character varying(255) DEFAULT ''::character varying NOT NULL,
    page_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    display_in_iframe_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    iframe_url character varying(255) DEFAULT ''::character varying NOT NULL,
    page_secure_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    CONSTRAINT ck_page_secure_yn CHECK (((page_secure_yn = 'y'::bpchar) OR (page_secure_yn = 'n'::bpchar))),
    CONSTRAINT dk_display_in_iframe_yn CHECK (((display_in_iframe_yn = 'y'::bpchar) OR (display_in_iframe_yn = 'n'::bpchar)))
);


--
-- TOC entry 11741 (class 1259 OID 34197)
-- Dependencies: 13648 13649 13650 13651 13652 13653 13654 27
-- Name: admin_page_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page_detail (
    page_detail_id integer DEFAULT nextval(('verilion.admin_page_detail_page_detail_id_s'::text)::regclass) NOT NULL,
    page_id integer DEFAULT 0 NOT NULL,
    page_detail_title character varying(255) DEFAULT ''::character varying NOT NULL,
    page_detail_contents text DEFAULT ''::text NOT NULL,
    ct_language_id integer DEFAULT 1 NOT NULL,
    page_detail_title_style_id integer DEFAULT 0 NOT NULL,
    page_detail_contents_style_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11742 (class 1259 OID 34209)
-- Dependencies: 27
-- Name: admin_page_detail_page_detail_id_s; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_detail_page_detail_id_s
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11743 (class 1259 OID 34211)
-- Dependencies: 13656 27
-- Name: admin_page_group; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page_group (
    admin_page_group_id integer NOT NULL,
    admin_page_group_name character varying(255),
    admin_page_group_description text,
    admin_page_group_active_yn character(1) DEFAULT 'y'::bpchar
);


--
-- TOC entry 11744 (class 1259 OID 34217)
-- Dependencies: 11743 27
-- Name: admin_page_group_admin_page_group_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_group_admin_page_group_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14317 (class 0 OID 0)
-- Dependencies: 11744
-- Name: admin_page_group_admin_page_group_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE admin_page_group_admin_page_group_id_seq OWNED BY admin_page_group.admin_page_group_id;


--
-- TOC entry 11745 (class 1259 OID 34219)
-- Dependencies: 27
-- Name: admin_page_group_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page_group_detail (
    admin_page_group_detail_id integer NOT NULL,
    admin_page_group_id integer,
    admin_page_id integer
);


--
-- TOC entry 11746 (class 1259 OID 34221)
-- Dependencies: 11745 27
-- Name: admin_page_group_detail_admin_page_group_detail_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_group_detail_admin_page_group_detail_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14318 (class 0 OID 0)
-- Dependencies: 11746
-- Name: admin_page_group_detail_admin_page_group_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE admin_page_group_detail_admin_page_group_detail_id_seq OWNED BY admin_page_group_detail.admin_page_group_detail_id;


--
-- TOC entry 11747 (class 1259 OID 34223)
-- Dependencies: 27
-- Name: admin_page_group_role; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page_group_role (
    admin_page_group_role_id integer NOT NULL,
    role_id bigint,
    admin_page_id bigint
);


--
-- TOC entry 11748 (class 1259 OID 34225)
-- Dependencies: 11747 27
-- Name: admin_page_group_role_admin_page_group_role_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_group_role_admin_page_group_role_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14319 (class 0 OID 0)
-- Dependencies: 11748
-- Name: admin_page_group_role_admin_page_group_role_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE admin_page_group_role_admin_page_group_role_id_seq OWNED BY admin_page_group_role.admin_page_group_role_id;


--
-- TOC entry 11749 (class 1259 OID 34227)
-- Dependencies: 27
-- Name: admin_page_page_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_page_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11750 (class 1259 OID 34229)
-- Dependencies: 27
-- Name: admin_page_role; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE admin_page_role (
    admin_page_role_id integer NOT NULL,
    admin_page_id integer,
    role_id integer
);


--
-- TOC entry 11751 (class 1259 OID 34231)
-- Dependencies: 11750 27
-- Name: admin_page_role_admin_page_role_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE admin_page_role_admin_page_role_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14320 (class 0 OID 0)
-- Dependencies: 11751
-- Name: admin_page_role_admin_page_role_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE admin_page_role_admin_page_role_id_seq OWNED BY admin_page_role.admin_page_role_id;


--
-- TOC entry 11752 (class 1259 OID 34233)
-- Dependencies: 27
-- Name: adult_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE adult_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = true;

--
-- TOC entry 11753 (class 1259 OID 34235)
-- Dependencies: 13660 13661 13662 13663 13664 13665 13666 13667 13668 27
-- Name: archive_page; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE archive_page (
    archive_page_id integer DEFAULT nextval(('verilion.archive_page_id_seq'::text)::regclass) NOT NULL,
    archive_page_name character varying(255) DEFAULT ''::character varying NOT NULL,
    archive_page_title character varying(255) DEFAULT ''::character varying NOT NULL,
    archive_page_content text DEFAULT ''::text NOT NULL,
    parent_id integer DEFAULT 0 NOT NULL,
    template_id integer DEFAULT 1 NOT NULL,
    archive_page_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    ct_access_level_id smallint DEFAULT 1 NOT NULL,
    CONSTRAINT ck_archive_page_active_yn CHECK (((archive_page_active_yn = 'y'::bpchar) OR (archive_page_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11754 (class 1259 OID 34249)
-- Dependencies: 13669 13670 13671 13672 13673 13674 13675 13676 13677 13678 13679 27
-- Name: archive_page_entry; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE archive_page_entry (
    archive_page_entry_id integer DEFAULT nextval(('verilion.archive_page_entry_id_seq'::text)::regclass) NOT NULL,
    archive_page_id integer DEFAULT 1 NOT NULL,
    archive_page_entry_content text DEFAULT ''::text NOT NULL,
    archive_page_entry_url character varying(255) DEFAULT ''::character varying NOT NULL,
    archive_page_entry_phone character varying(255) DEFAULT ''::character varying NOT NULL,
    archive_page_entry_fax character varying(255) DEFAULT ''::character varying NOT NULL,
    archive_page_entry_email character varying(255) DEFAULT ''::character varying NOT NULL,
    start_date date DEFAULT now() NOT NULL,
    end_date date,
    archive_page_entry_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    archive_page_entry_title character varying(255) DEFAULT ''::character varying NOT NULL,
    CONSTRAINT ck_archive_page_entry_active_yn CHECK (((archive_page_entry_active_yn = 'y'::bpchar) OR (archive_page_entry_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11755 (class 1259 OID 34265)
-- Dependencies: 27
-- Name: archive_page_entry_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE archive_page_entry_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11756 (class 1259 OID 34267)
-- Dependencies: 27
-- Name: archive_page_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE archive_page_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11757 (class 1259 OID 34269)
-- Dependencies: 13681 27
-- Name: banner_clicks; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE banner_clicks (
    banner_clicks_id integer NOT NULL,
    banner_id integer,
    ip_address character varying(15),
    datetime timestamp without time zone DEFAULT now()
);


--
-- TOC entry 11758 (class 1259 OID 34272)
-- Dependencies: 11757 27
-- Name: banner_clicks_banner_clicks_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE banner_clicks_banner_clicks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14321 (class 0 OID 0)
-- Dependencies: 11758
-- Name: banner_clicks_banner_clicks_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE banner_clicks_banner_clicks_id_seq OWNED BY banner_clicks.banner_clicks_id;


--
-- TOC entry 11759 (class 1259 OID 34274)
-- Dependencies: 13683 27
-- Name: banner_impressions; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE banner_impressions (
    banner_impressions_id integer NOT NULL,
    banner_id integer,
    datetime timestamp without time zone DEFAULT now(),
    ip_address character varying(20)
);


--
-- TOC entry 11760 (class 1259 OID 34277)
-- Dependencies: 11759 27
-- Name: banner_impressions_banner_impressions_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE banner_impressions_banner_impressions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14322 (class 0 OID 0)
-- Dependencies: 11760
-- Name: banner_impressions_banner_impressions_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE banner_impressions_banner_impressions_id_seq OWNED BY banner_impressions.banner_impressions_id;


--
-- TOC entry 11761 (class 1259 OID 34279)
-- Dependencies: 13685 27
-- Name: banners; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE banners (
    banner_id integer NOT NULL,
    banner_name character varying(255),
    url text,
    from_date date,
    to_date date,
    impressions bigint,
    clicks bigint,
    banner_file character varying(255),
    banner_active_yn character(1) DEFAULT 'n'::bpchar,
    ct_banner_position_id integer,
    ct_banner_sizes_id integer
);


--
-- TOC entry 11762 (class 1259 OID 34285)
-- Dependencies: 11761 27
-- Name: banners_banner_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE banners_banner_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14323 (class 0 OID 0)
-- Dependencies: 11762
-- Name: banners_banner_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE banners_banner_id_seq OWNED BY banners.banner_id;


--
-- TOC entry 11763 (class 1259 OID 34287)
-- Dependencies: 27
-- Name: breed_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE breed_id_seq
    START WITH 1218
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11764 (class 1259 OID 34289)
-- Dependencies: 27
-- Name: breedclub_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE breedclub_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11765 (class 1259 OID 34291)
-- Dependencies: 27
-- Name: career_locations; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE career_locations (
    career_locations_id integer NOT NULL,
    career_location character varying(255)
);


--
-- TOC entry 11766 (class 1259 OID 34293)
-- Dependencies: 11765 27
-- Name: career_locations_career_locations_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE career_locations_career_locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14324 (class 0 OID 0)
-- Dependencies: 11766
-- Name: career_locations_career_locations_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE career_locations_career_locations_id_seq OWNED BY career_locations.career_locations_id;


--
-- TOC entry 11767 (class 1259 OID 34295)
-- Dependencies: 27
-- Name: career_type; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE career_type (
    career_type_id integer NOT NULL,
    career_type character varying(255)
);


--
-- TOC entry 11768 (class 1259 OID 34297)
-- Dependencies: 11767 27
-- Name: career_type_career_type_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE career_type_career_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14325 (class 0 OID 0)
-- Dependencies: 11768
-- Name: career_type_career_type_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE career_type_career_type_id_seq OWNED BY career_type.career_type_id;


--
-- TOC entry 11769 (class 1259 OID 34299)
-- Dependencies: 27
-- Name: careers; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE careers (
    career_id integer NOT NULL,
    career_title character varying(255),
    career_text text,
    career_locations_id bigint,
    career_type smallint
);


--
-- TOC entry 11770 (class 1259 OID 34304)
-- Dependencies: 11769 27
-- Name: careers_career_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE careers_career_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14326 (class 0 OID 0)
-- Dependencies: 11770
-- Name: careers_career_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE careers_career_id_seq OWNED BY careers.career_id;


--
-- TOC entry 11771 (class 1259 OID 34306)
-- Dependencies: 13690 27
-- Name: category; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE category (
    category_id integer NOT NULL,
    category_name character varying(255),
    parent_category_id bigint,
    category_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11772 (class 1259 OID 34309)
-- Dependencies: 11771 27
-- Name: category_category_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE category_category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14327 (class 0 OID 0)
-- Dependencies: 11772
-- Name: category_category_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE category_category_id_seq OWNED BY category.category_id;


SET default_with_oids = true;

--
-- TOC entry 11773 (class 1259 OID 34311)
-- Dependencies: 13691 27
-- Name: class_objects; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE class_objects (
    class_object_id integer DEFAULT nextval(('verilion.class_objects_id_seq'::text)::regclass) NOT NULL,
    class_object_name character varying(255),
    class_object character varying(255)
);


--
-- TOC entry 11774 (class 1259 OID 34317)
-- Dependencies: 27
-- Name: class_objects_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE class_objects_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11775 (class 1259 OID 34319)
-- Dependencies: 13693 27
-- Name: components; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE components (
    component_id integer NOT NULL,
    component_name character varying(255),
    url character varying(255),
    component_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11776 (class 1259 OID 34325)
-- Dependencies: 11775 27
-- Name: components_component_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE components_component_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14328 (class 0 OID 0)
-- Dependencies: 11776
-- Name: components_component_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE components_component_id_seq OWNED BY components.component_id;


SET default_with_oids = true;

--
-- TOC entry 11777 (class 1259 OID 34327)
-- Dependencies: 13694 13695 13696 27
-- Name: ct_access_level; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_access_level (
    ct_access_level_id integer DEFAULT nextval(('ct_access_level_ct_access_le'::text)::regclass) NOT NULL,
    ct_access_level_name character varying(255) DEFAULT ''::character varying NOT NULL,
    ct_access_level integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11778 (class 1259 OID 34332)
-- Dependencies: 27
-- Name: ct_access_level_ct_access_le; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_access_level_ct_access_le
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11779 (class 1259 OID 34334)
-- Dependencies: 13697 13698 27
-- Name: ct_address_type; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_address_type (
    ct_address_type_id integer DEFAULT nextval(('ct_address_type_ct_address_t'::text)::regclass) NOT NULL,
    ct_address_type_name character varying(50) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11780 (class 1259 OID 34338)
-- Dependencies: 27
-- Name: ct_address_type_ct_address_t; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_address_type_ct_address_t
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11781 (class 1259 OID 34340)
-- Dependencies: 27
-- Name: ct_banner_position; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_banner_position (
    ct_banner_position_id integer NOT NULL,
    ct_banner_position character varying(255)
);


--
-- TOC entry 11782 (class 1259 OID 34342)
-- Dependencies: 11781 27
-- Name: ct_banner_position_ct_banner_position_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_banner_position_ct_banner_position_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14329 (class 0 OID 0)
-- Dependencies: 11782
-- Name: ct_banner_position_ct_banner_position_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE ct_banner_position_ct_banner_position_id_seq OWNED BY ct_banner_position.ct_banner_position_id;


--
-- TOC entry 11783 (class 1259 OID 34344)
-- Dependencies: 27
-- Name: ct_banner_sizes; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_banner_sizes (
    ct_banner_sizes_id integer NOT NULL,
    ct_banner_sizes_desc character varying(255),
    ct_banner_sizes_height integer,
    ct_banner_sizes_width integer
);


--
-- TOC entry 11784 (class 1259 OID 34346)
-- Dependencies: 11783 27
-- Name: ct_banner_sizes_ct_banner_sizes_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_banner_sizes_ct_banner_sizes_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14330 (class 0 OID 0)
-- Dependencies: 11784
-- Name: ct_banner_sizes_ct_banner_sizes_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE ct_banner_sizes_ct_banner_sizes_id_seq OWNED BY ct_banner_sizes.ct_banner_sizes_id;


SET default_with_oids = true;

--
-- TOC entry 11785 (class 1259 OID 34348)
-- Dependencies: 13701 13702 27
-- Name: ct_country; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_country (
    ct_country_id integer DEFAULT nextval(('ct_country_ct_country_id_seq'::text)::regclass) NOT NULL,
    ct_country_name character varying(50) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11786 (class 1259 OID 34352)
-- Dependencies: 27
-- Name: ct_country_ct_country_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_country_ct_country_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11787 (class 1259 OID 34354)
-- Dependencies: 13703 13704 13705 27
-- Name: ct_credit_card; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_credit_card (
    ct_credit_card_id integer DEFAULT nextval(('ct_credit_card_ct_credit_car'::text)::regclass) NOT NULL,
    ct_credit_card_name character varying(50) DEFAULT ''::character varying NOT NULL,
    ct_credit_card_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL
);


--
-- TOC entry 11788 (class 1259 OID 34359)
-- Dependencies: 27
-- Name: ct_credit_card_ct_credit_car; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_credit_card_ct_credit_car
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11789 (class 1259 OID 34361)
-- Dependencies: 13706 13707 13708 13709 27
-- Name: ct_languages; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_languages (
    ct_language_id integer DEFAULT nextval(('ct_languages_ct_language_id_'::text)::regclass) NOT NULL,
    ct_language_name character varying(255) DEFAULT ''::character varying NOT NULL,
    ct_language_jcode character(2) DEFAULT ''::bpchar NOT NULL,
    ct_language_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL
);


--
-- TOC entry 11790 (class 1259 OID 34367)
-- Dependencies: 27
-- Name: ct_languages_ct_language_id_; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_languages_ct_language_id_
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11791 (class 1259 OID 34369)
-- Dependencies: 13710 13711 27
-- Name: ct_menu_alignment_types; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_menu_alignment_types (
    ct_menu_alignment_type_id integer DEFAULT nextval(('ct_menu_alignment_types_ct_m'::text)::regclass) NOT NULL,
    ct_menu_alignment_type_name character varying(255) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11792 (class 1259 OID 34373)
-- Dependencies: 27
-- Name: ct_menu_alignment_types_ct_m; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_menu_alignment_types_ct_m
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11793 (class 1259 OID 34375)
-- Dependencies: 13712 13713 27
-- Name: ct_menu_types; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_menu_types (
    ct_menu_type_id integer DEFAULT nextval(('verilion.ct_menu_types_ct_menu_type_i'::text)::regclass) NOT NULL,
    ct_menu_type_name character varying(255) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11794 (class 1259 OID 34379)
-- Dependencies: 27
-- Name: ct_menu_types_ct_menu_type_i; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_menu_types_ct_menu_type_i
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11795 (class 1259 OID 34381)
-- Dependencies: 13714 13715 13716 27
-- Name: ct_month; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_month (
    ct_month_id integer DEFAULT nextval(('ct_month_ct_month_id_seq'::text)::regclass) NOT NULL,
    ct_month_name character varying(15) DEFAULT ''::character varying NOT NULL,
    ct_month_abb character varying(4) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11796 (class 1259 OID 34386)
-- Dependencies: 27
-- Name: ct_month_ct_month_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_month_ct_month_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11797 (class 1259 OID 34388)
-- Dependencies: 13717 13718 13719 13720 13721 27
-- Name: ct_packages; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_packages (
    ct_package_id integer DEFAULT nextval(('ct_packages_ct_package_id_se'::text)::regclass) NOT NULL,
    ct_package_name character varying(50) DEFAULT ''::character varying NOT NULL,
    ct_package_description text DEFAULT ''::text NOT NULL,
    ct_package_price double precision DEFAULT (0)::double precision NOT NULL,
    ct_package_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    sample_link character varying(255)
);


--
-- TOC entry 11798 (class 1259 OID 34398)
-- Dependencies: 27
-- Name: ct_packages_ct_package_id_se; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_packages_ct_package_id_se
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11799 (class 1259 OID 34400)
-- Dependencies: 13722 13723 27
-- Name: ct_phone_type; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_phone_type (
    ct_phone_type_id integer DEFAULT nextval(('ct_phone_type_ct_phone_type_'::text)::regclass) NOT NULL,
    ct_phone_type_name character varying(50) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11800 (class 1259 OID 34404)
-- Dependencies: 27
-- Name: ct_phone_type_ct_phone_type_; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_phone_type_ct_phone_type_
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11801 (class 1259 OID 34406)
-- Dependencies: 13724 13725 13726 27
-- Name: ct_province_state; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_province_state (
    ct_province_state_id integer DEFAULT nextval(('ct_province_state_ct_provinc'::text)::regclass) NOT NULL,
    ct_province_state_name character varying(50) DEFAULT ''::character varying NOT NULL,
    ct_province_state_abb character(2) DEFAULT ''::bpchar NOT NULL
);


--
-- TOC entry 11802 (class 1259 OID 34411)
-- Dependencies: 27
-- Name: ct_province_state_ct_provinc; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_province_state_ct_provinc
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11803 (class 1259 OID 34413)
-- Dependencies: 13727 13728 27
-- Name: ct_salutation; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_salutation (
    ct_salutation_id integer DEFAULT nextval(('ct_salutation_ct_salutation_'::text)::regclass) NOT NULL,
    ct_salutation_name character varying(50) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11804 (class 1259 OID 34417)
-- Dependencies: 27
-- Name: ct_salutation_ct_salutation_; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_salutation_ct_salutation_
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11805 (class 1259 OID 34419)
-- Dependencies: 13729 13730 27
-- Name: ct_term; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE ct_term (
    ct_term_id integer DEFAULT nextval(('verilion.ct_term_ct_term_id_seq'::text)::regclass) NOT NULL,
    ct_term_name character varying(100) DEFAULT ''::character varying NOT NULL,
    ct_term_quantity integer
);


--
-- TOC entry 11806 (class 1259 OID 34423)
-- Dependencies: 27
-- Name: ct_term_ct_term_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE ct_term_ct_term_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11807 (class 1259 OID 34425)
-- Dependencies: 13732 13733 13734 27
-- Name: currency; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE currency (
    currency_id integer NOT NULL,
    currency_is_default_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    currency_name character varying(255) NOT NULL,
    currency_exchange_rate real DEFAULT 1.0 NOT NULL,
    currency_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL
);


--
-- TOC entry 11808 (class 1259 OID 34430)
-- Dependencies: 11807 27
-- Name: currency_currency_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE currency_currency_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14331 (class 0 OID 0)
-- Dependencies: 11808
-- Name: currency_currency_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE currency_currency_id_seq OWNED BY currency.currency_id;


SET default_with_oids = true;

--
-- TOC entry 11809 (class 1259 OID 34432)
-- Dependencies: 13735 13736 13737 13738 13739 13740 13741 13742 13743 13744 13745 13746 13747 13748 13749 13750 13751 13752 13753 13754 13755 13756 13757 13758 13759 13760 27
-- Name: customer; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer (
    customer_id integer DEFAULT nextval(('verilion.customer_customer_id_seq'::text)::regclass) NOT NULL,
    ct_salutation_id integer DEFAULT 1 NOT NULL,
    customer_first_name character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_last_name character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_company_name character varying(50) DEFAULT 'unknown name'::character varying NOT NULL,
    customer_website_url character varying(100) DEFAULT ''::character varying NOT NULL,
    customer_add_to_mailing_list smallint DEFAULT (0)::smallint NOT NULL,
    ct_term_id integer DEFAULT 1 NOT NULL,
    ct_credit_card_id integer DEFAULT 1 NOT NULL,
    customer_credit_card_number character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_card_expiry_month integer DEFAULT 0 NOT NULL,
    customer_credit_card_expiry_year integer DEFAULT 0 NOT NULL,
    customer_name_on_card character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_registration_date date DEFAULT '0001-01-01'::date NOT NULL,
    customer_next_billing_date date DEFAULT '0001-01-01'::date NOT NULL,
    customer_password character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    customer_access_level smallint DEFAULT (0)::smallint NOT NULL,
    customer_company_description text DEFAULT ''::text NOT NULL,
    ct_package_id integer DEFAULT 1 NOT NULL,
    image_id integer DEFAULT 0 NOT NULL,
    customer_isnew_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    username character varying(255) DEFAULT ''::character varying,
    CONSTRAINT ck_customer_active_yn CHECK ((((customer_active_yn = 'y'::bpchar) OR (customer_active_yn = 'n'::bpchar)) OR (customer_active_yn = 'p'::bpchar))),
    CONSTRAINT ck_customer_add_to_mailing_list CHECK (((customer_add_to_mailing_list = 1) OR (customer_add_to_mailing_list = 0))),
    CONSTRAINT ck_customer_isnew_yn CHECK (((customer_isnew_yn = 'y'::bpchar) OR (customer_isnew_yn = 'n'::bpchar)))
);


--
-- TOC entry 11810 (class 1259 OID 34463)
-- Dependencies: 13761 13762 13763 13764 13765 13766 13767 13768 13769 13770 27
-- Name: customer_address_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer_address_detail (
    customer_address_id integer DEFAULT nextval(('verilion.customer_address_detail_cust'::text)::regclass) NOT NULL,
    ct_address_type_id integer DEFAULT 0 NOT NULL,
    customer_address character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_town_city character varying(50) DEFAULT ''::character varying NOT NULL,
    ct_province_state_id integer DEFAULT 0 NOT NULL,
    ct_country_id integer DEFAULT 0 NOT NULL,
    customer_zip_postal character varying(50) DEFAULT ''::character varying NOT NULL,
    primary_address_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    customer_id integer DEFAULT 0 NOT NULL,
    CONSTRAINT ck_primary_address_yn CHECK (((primary_address_yn = 'y'::bpchar) OR (primary_address_yn = 'n'::bpchar)))
);


--
-- TOC entry 11811 (class 1259 OID 34475)
-- Dependencies: 27
-- Name: customer_address_detail_cust; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_address_detail_cust
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11812 (class 1259 OID 34477)
-- Dependencies: 27
-- Name: customer_breed_customer_breed_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_breed_customer_breed_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11813 (class 1259 OID 34479)
-- Dependencies: 27
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_customer_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11814 (class 1259 OID 34481)
-- Dependencies: 13771 13772 13773 13774 13775 27
-- Name: customer_email_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer_email_detail (
    customer_email_id integer DEFAULT nextval(('customer_email_detail_custom'::text)::regclass) NOT NULL,
    customer_id integer DEFAULT 0 NOT NULL,
    customer_email character varying(100) DEFAULT ''::character varying NOT NULL,
    customer_email_default_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_customer_email_default_yn CHECK (((customer_email_default_yn = 'y'::bpchar) OR (customer_email_default_yn = 'n'::bpchar)))
);


--
-- TOC entry 11815 (class 1259 OID 34488)
-- Dependencies: 27
-- Name: customer_email_detail_custom; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_email_detail_custom
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11816 (class 1259 OID 34490)
-- Dependencies: 13777 13778 13779 13780 27
-- Name: customer_messages; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer_messages (
    customer_message_id integer NOT NULL,
    from_user_id integer,
    to_user_id integer,
    date_time time without time zone,
    message text,
    is_read_yn character(1) DEFAULT 'n'::bpchar,
    subject character varying(255) DEFAULT ''::character varying,
    time_sent time without time zone DEFAULT now(),
    date_sent time without time zone DEFAULT now()
);


--
-- TOC entry 11817 (class 1259 OID 34499)
-- Dependencies: 11816 27
-- Name: customer_messages_customer_message_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_messages_customer_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14332 (class 0 OID 0)
-- Dependencies: 11817
-- Name: customer_messages_customer_message_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE customer_messages_customer_message_id_seq OWNED BY customer_messages.customer_message_id;


--
-- TOC entry 11818 (class 1259 OID 34501)
-- Dependencies: 13782 13783 13784 13785 27
-- Name: customer_messages_sent; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer_messages_sent (
    customer_message_sent_id integer NOT NULL,
    from_user_id integer,
    to_user_id integer,
    time_sent time without time zone DEFAULT now(),
    message text,
    is_read_yn character(1) DEFAULT 'n'::bpchar,
    subject character varying(255) DEFAULT ''::character varying,
    date_sent date DEFAULT now()
);


--
-- TOC entry 11819 (class 1259 OID 34510)
-- Dependencies: 11818 27
-- Name: customer_messages_sent_customer_message_sent_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_messages_sent_customer_message_sent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14333 (class 0 OID 0)
-- Dependencies: 11819
-- Name: customer_messages_sent_customer_message_sent_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE customer_messages_sent_customer_message_sent_id_seq OWNED BY customer_messages_sent.customer_message_sent_id;


SET default_with_oids = true;

--
-- TOC entry 11820 (class 1259 OID 34512)
-- Dependencies: 13786 13787 13788 13789 13790 13791 13792 27
-- Name: customer_phone_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE customer_phone_detail (
    customer_phone_id integer DEFAULT nextval(('verilion.customer_phone_detail_custom'::text)::regclass) NOT NULL,
    ct_phone_type_id integer DEFAULT 0 NOT NULL,
    customer_phone character varying(50) DEFAULT ''::character varying NOT NULL,
    customer_phone_extension character varying(10) DEFAULT ''::character varying NOT NULL,
    customer_phone_default_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    customer_id integer DEFAULT 0 NOT NULL,
    CONSTRAINT ck_customer_phone_default_yn CHECK (((customer_phone_default_yn = 'y'::bpchar) OR (customer_phone_default_yn = 'n'::bpchar)))
);


--
-- TOC entry 11821 (class 1259 OID 34521)
-- Dependencies: 27
-- Name: customer_phone_detail_custom; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE customer_phone_detail_custom
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11822 (class 1259 OID 34523)
-- Dependencies: 27
-- Name: document_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE document_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11823 (class 1259 OID 34525)
-- Dependencies: 13793 13794 13795 13796 27
-- Name: documents; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE documents (
    document_id integer DEFAULT nextval(('document_id_seq'::text)::regclass) NOT NULL,
    document_mime_type character varying(255),
    document_data bytea,
    document_name character varying(255),
    page_id integer,
    owner_id integer DEFAULT 0 NOT NULL,
    document_title character varying(255),
    document_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_document_active_yn CHECK (((document_active_yn = 'y'::bpchar) OR (document_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11824 (class 1259 OID 34534)
-- Dependencies: 13797 13798 27
-- Name: errors; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE errors (
    error_id integer DEFAULT nextval(('verilion.errors_error_id_seq'::text)::regclass) NOT NULL,
    error_date timestamp without time zone NOT NULL,
    error_message text DEFAULT ''::text NOT NULL
);


--
-- TOC entry 11825 (class 1259 OID 34541)
-- Dependencies: 27
-- Name: errors_error_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE errors_error_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11826 (class 1259 OID 34543)
-- Dependencies: 13800 27
-- Name: events; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE events (
    event_id integer NOT NULL,
    event_name character varying(255),
    description text,
    start_date_time timestamp without time zone,
    end_date_time timestamp without time zone,
    event_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11827 (class 1259 OID 34549)
-- Dependencies: 11826 27
-- Name: events_event_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE events_event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14334 (class 0 OID 0)
-- Dependencies: 11827
-- Name: events_event_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE events_event_id_seq OWNED BY events.event_id;


--
-- TOC entry 11828 (class 1259 OID 34551)
-- Dependencies: 13802 27
-- Name: faq; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE faq (
    faq_id integer NOT NULL,
    faq_categories_id integer,
    question text,
    answer text,
    username character varying(255),
    active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11829 (class 1259 OID 34557)
-- Dependencies: 13804 27
-- Name: faq_categories; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE faq_categories (
    faq_categories_id integer NOT NULL,
    faq_categories_name character varying(255),
    parent_id integer,
    active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11830 (class 1259 OID 34560)
-- Dependencies: 11829 27
-- Name: faq_categories_faq_categories_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE faq_categories_faq_categories_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14335 (class 0 OID 0)
-- Dependencies: 11830
-- Name: faq_categories_faq_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE faq_categories_faq_categories_id_seq OWNED BY faq_categories.faq_categories_id;


--
-- TOC entry 11831 (class 1259 OID 34562)
-- Dependencies: 11828 27
-- Name: faq_faq_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE faq_faq_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14336 (class 0 OID 0)
-- Dependencies: 11831
-- Name: faq_faq_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE faq_faq_id_seq OWNED BY faq.faq_id;


SET default_with_oids = true;

--
-- TOC entry 11832 (class 1259 OID 34564)
-- Dependencies: 13805 13806 13807 13808 13809 13810 27
-- Name: images; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE images (
    image_id integer DEFAULT nextval(('verilion.images_image_id_seq'::text)::regclass) NOT NULL,
    height integer DEFAULT 0 NOT NULL,
    width integer DEFAULT 0 NOT NULL,
    file_type character varying(30) DEFAULT ''::character varying NOT NULL,
    customer_id integer DEFAULT 0 NOT NULL,
    caption character varying(255),
    image_data bytea,
    thumbnail_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11833 (class 1259 OID 34575)
-- Dependencies: 27
-- Name: images_image_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE images_image_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11834 (class 1259 OID 34577)
-- Dependencies: 13812 13813 27
-- Name: item; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item (
    item_id integer NOT NULL,
    item_name character varying(255),
    item_price double precision,
    item_short_description text,
    item_long_description text,
    item_inventory_on_hand bigint,
    item_inventory_threshold bigint,
    item_sku character varying(255),
    item_taxable_yn character(1) DEFAULT 'n'::bpchar,
    item_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11835 (class 1259 OID 34584)
-- Dependencies: 13815 27
-- Name: item_attributes; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item_attributes (
    item_attributes_id integer NOT NULL,
    item_id bigint,
    item_attributes_name character varying(255),
    item_attributes_active_yn character(1) DEFAULT 'n'::bpchar,
    item_attributes_order integer
);


--
-- TOC entry 11836 (class 1259 OID 34587)
-- Dependencies: 11835 27
-- Name: item_attributes_item_attributes_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_attributes_item_attributes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14337 (class 0 OID 0)
-- Dependencies: 11836
-- Name: item_attributes_item_attributes_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_attributes_item_attributes_id_seq OWNED BY item_attributes.item_attributes_id;


--
-- TOC entry 11837 (class 1259 OID 34589)
-- Dependencies: 27
-- Name: item_category; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item_category (
    item_category_id integer NOT NULL,
    item_id bigint,
    category_id bigint
);


--
-- TOC entry 11838 (class 1259 OID 34591)
-- Dependencies: 11837 27
-- Name: item_category_item_category_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_category_item_category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14338 (class 0 OID 0)
-- Dependencies: 11838
-- Name: item_category_item_category_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_category_item_category_id_seq OWNED BY item_category.item_category_id;


SET default_with_oids = true;

--
-- TOC entry 11839 (class 1259 OID 34593)
-- Dependencies: 13818 13819 13820 13821 13822 27
-- Name: item_images; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item_images (
    item_image_id integer NOT NULL,
    height integer DEFAULT 0 NOT NULL,
    width integer DEFAULT 0 NOT NULL,
    file_type character varying(30) DEFAULT ''::character varying NOT NULL,
    item_id integer DEFAULT 0 NOT NULL,
    caption character varying(255),
    image_data bytea,
    thumbnail_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11840 (class 1259 OID 34603)
-- Dependencies: 11839 27
-- Name: item_images_item_image_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_images_item_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14339 (class 0 OID 0)
-- Dependencies: 11840
-- Name: item_images_item_image_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_images_item_image_id_seq OWNED BY item_images.item_image_id;


--
-- TOC entry 11841 (class 1259 OID 34605)
-- Dependencies: 11834 27
-- Name: item_item_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_item_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14340 (class 0 OID 0)
-- Dependencies: 11841
-- Name: item_item_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_item_id_seq OWNED BY item.item_id;


SET default_with_oids = false;

--
-- TOC entry 11842 (class 1259 OID 34607)
-- Dependencies: 27
-- Name: item_shipping; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item_shipping (
    item_shipping_id integer NOT NULL,
    item_id bigint,
    shipping_id bigint,
    base_rate double precision,
    additional_rate double precision
);


--
-- TOC entry 11843 (class 1259 OID 34609)
-- Dependencies: 11842 27
-- Name: item_shipping_item_shipping_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_shipping_item_shipping_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14341 (class 0 OID 0)
-- Dependencies: 11843
-- Name: item_shipping_item_shipping_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_shipping_item_shipping_id_seq OWNED BY item_shipping.item_shipping_id;


--
-- TOC entry 11844 (class 1259 OID 34611)
-- Dependencies: 13825 27
-- Name: item_special; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE item_special (
    item_special_id integer NOT NULL,
    item_id bigint,
    special_start_date date,
    special_end_date date,
    special_price double precision,
    special_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11845 (class 1259 OID 34614)
-- Dependencies: 11844 27
-- Name: item_special_item_special_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE item_special_item_special_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14342 (class 0 OID 0)
-- Dependencies: 11845
-- Name: item_special_item_special_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE item_special_item_special_id_seq OWNED BY item_special.item_special_id;


--
-- TOC entry 11846 (class 1259 OID 34616)
-- Dependencies: 27
-- Name: jsp_template_page; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE jsp_template_page (
    jsp_template_page_id integer NOT NULL,
    page_id integer,
    jsp_template_id integer
);


--
-- TOC entry 11847 (class 1259 OID 34618)
-- Dependencies: 11846 27
-- Name: jsp_template_page_jsp_template_page_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE jsp_template_page_jsp_template_page_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14343 (class 0 OID 0)
-- Dependencies: 11847
-- Name: jsp_template_page_jsp_template_page_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE jsp_template_page_jsp_template_page_id_seq OWNED BY jsp_template_page.jsp_template_page_id;


--
-- TOC entry 11848 (class 1259 OID 34620)
-- Dependencies: 13828 27
-- Name: jsp_templates; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE jsp_templates (
    jsp_template_id integer NOT NULL,
    jsp_template_name character varying(255),
    jsp_template_description text,
    jsp_template_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11849 (class 1259 OID 34626)
-- Dependencies: 11848 27
-- Name: jsp_templates_jsp_template_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE jsp_templates_jsp_template_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14344 (class 0 OID 0)
-- Dependencies: 11849
-- Name: jsp_templates_jsp_template_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE jsp_templates_jsp_template_id_seq OWNED BY jsp_templates.jsp_template_id;


--
-- TOC entry 11850 (class 1259 OID 34628)
-- Dependencies: 13830 27
-- Name: links; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE links (
    link_id integer NOT NULL,
    link_cat_id integer,
    url character varying(255),
    description text,
    active_yn character(1) DEFAULT 'n'::bpchar,
    title character varying(255)
);


--
-- TOC entry 11851 (class 1259 OID 34634)
-- Dependencies: 13832 27
-- Name: links_categories; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE links_categories (
    links_cat_id integer NOT NULL,
    title character varying(255),
    active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11852 (class 1259 OID 34637)
-- Dependencies: 11851 27
-- Name: links_categories_links_cat_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE links_categories_links_cat_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14345 (class 0 OID 0)
-- Dependencies: 11852
-- Name: links_categories_links_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE links_categories_links_cat_id_seq OWNED BY links_categories.links_cat_id;


--
-- TOC entry 11853 (class 1259 OID 34639)
-- Dependencies: 11850 27
-- Name: links_link_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE links_link_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14346 (class 0 OID 0)
-- Dependencies: 11853
-- Name: links_link_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE links_link_id_seq OWNED BY links.link_id;


--
-- TOC entry 11854 (class 1259 OID 34641)
-- Dependencies: 27
-- Name: litters_litter_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE litters_litter_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11855 (class 1259 OID 34643)
-- Dependencies: 27
-- Name: logged_in_users; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE logged_in_users (
    logged_in_user_id integer NOT NULL,
    username character varying(255),
    session_id character varying(255)
);


--
-- TOC entry 11856 (class 1259 OID 34648)
-- Dependencies: 11855 27
-- Name: logged_in_users_logged_in_user_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE logged_in_users_logged_in_user_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14347 (class 0 OID 0)
-- Dependencies: 11856
-- Name: logged_in_users_logged_in_user_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE logged_in_users_logged_in_user_id_seq OWNED BY logged_in_users.logged_in_user_id;


SET default_with_oids = true;

--
-- TOC entry 11857 (class 1259 OID 34650)
-- Dependencies: 13834 13835 13836 27
-- Name: mailing_lists; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE mailing_lists (
    list_id integer DEFAULT nextval(('verilion.mailing_lists_list_id_seq'::text)::regclass) NOT NULL,
    customer_id integer DEFAULT 0 NOT NULL,
    breed_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11858 (class 1259 OID 34655)
-- Dependencies: 27
-- Name: mailing_lists_list_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE mailing_lists_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11859 (class 1259 OID 34657)
-- Dependencies: 13837 13838 13839 13840 13841 13842 13843 13844 13845 27
-- Name: menu; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE menu (
    menu_id integer DEFAULT nextval(('verilion.menu_menu_id_seq'::text)::regclass) NOT NULL,
    menu_name character varying(255) DEFAULT ''::character varying NOT NULL,
    menu_divider character varying(10) DEFAULT ''::character varying NOT NULL,
    ct_menu_type_id integer DEFAULT 1 NOT NULL,
    ct_menu_alignment_type_id integer DEFAULT 0 NOT NULL,
    menu_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    style_id integer DEFAULT 0 NOT NULL,
    menu_tag character varying(255) DEFAULT ''::character varying NOT NULL,
    CONSTRAINT ck_menu_active_yn CHECK (((menu_active_yn = 'y'::bpchar) OR (menu_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11860 (class 1259 OID 34671)
-- Dependencies: 13846 13847 13848 13849 13850 13851 13852 13853 13854 13855 13856 27
-- Name: menu_item; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE menu_item (
    menu_item_id integer DEFAULT nextval(('verilion.menu_item_menu_item_id_seq'::text)::regclass) NOT NULL,
    menu_id integer DEFAULT 0 NOT NULL,
    menu_item_action text DEFAULT ''::text NOT NULL,
    menu_item_description text DEFAULT ''::text NOT NULL,
    menu_item_order integer DEFAULT 0 NOT NULL,
    menu_item_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    page_id integer DEFAULT 0,
    menu_item_is_heading character(1) DEFAULT 'n'::bpchar,
    menu_item_is_spacer character(1) DEFAULT 'n'::bpchar,
    ct_access_level_id integer DEFAULT 0,
    component_id bigint,
    CONSTRAINT ck_menu_item_active_yn CHECK (((menu_item_active_yn = 'y'::bpchar) OR (menu_item_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11861 (class 1259 OID 34687)
-- Dependencies: 13857 13858 13859 13860 13861 27
-- Name: menu_item_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE menu_item_detail (
    menu_item_detail_id integer DEFAULT nextval(('verilion.menu_item_detail_menu_item_d'::text)::regclass) NOT NULL,
    menu_item_id integer DEFAULT 0 NOT NULL,
    ct_language_id integer DEFAULT 0 NOT NULL,
    menu_item_detail_name character varying(255) DEFAULT ''::character varying NOT NULL,
    menu_item_detail_tooltip character varying(255) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11862 (class 1259 OID 34697)
-- Dependencies: 27
-- Name: menu_item_detail_menu_item_d; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE menu_item_detail_menu_item_d
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11863 (class 1259 OID 34699)
-- Dependencies: 27
-- Name: menu_item_menu_item_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE menu_item_menu_item_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11864 (class 1259 OID 34701)
-- Dependencies: 27
-- Name: menu_menu_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE menu_menu_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11865 (class 1259 OID 34703)
-- Dependencies: 13863 27
-- Name: message_queue; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE message_queue (
    message_queue_id integer NOT NULL,
    message_queue_to character varying(255),
    message_queue_from character varying(255),
    message_queue_message text,
    message_queue_sent_date timestamp without time zone,
    message_queue_send_date timestamp without time zone,
    message_queue_sent_yn character(1) DEFAULT 'n'::bpchar,
    message_queue_subject character varying(255)
);


--
-- TOC entry 11866 (class 1259 OID 34709)
-- Dependencies: 11865 27
-- Name: message_queue_message_queue_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE message_queue_message_queue_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14348 (class 0 OID 0)
-- Dependencies: 11866
-- Name: message_queue_message_queue_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE message_queue_message_queue_id_seq OWNED BY message_queue.message_queue_id;


SET default_with_oids = true;

--
-- TOC entry 11867 (class 1259 OID 34711)
-- Dependencies: 13864 13865 13866 13867 13868 27
-- Name: module_position; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE module_position (
    module_position_id integer DEFAULT nextval(('verilion.module_postion_module_position_id_seq'::text)::regclass) NOT NULL,
    module_position_name character varying(255) DEFAULT ''::character varying NOT NULL,
    module_position_tag character varying(255) DEFAULT ''::character varying NOT NULL,
    module_position_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_module_position_active_yn CHECK (((module_position_active_yn = 'y'::bpchar) OR (module_position_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11868 (class 1259 OID 34721)
-- Dependencies: 27
-- Name: module_postion_module_position_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE module_postion_module_position_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11869 (class 1259 OID 34723)
-- Dependencies: 13869 13870 13871 13872 13873 27
-- Name: modules; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE modules (
    module_id integer DEFAULT nextval(('verilion.modules_module_id_seq'::text)::regclass) NOT NULL,
    module_name character varying(255) DEFAULT ''::character varying NOT NULL,
    module_position_id integer DEFAULT 0 NOT NULL,
    module_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    module_class_name character varying(255),
    jarfile character varying(255),
    CONSTRAINT ck_module_active_yn CHECK (((module_active_yn = 'y'::bpchar) OR (module_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11870 (class 1259 OID 34733)
-- Dependencies: 27
-- Name: modules_module_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE modules_module_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11871 (class 1259 OID 34735)
-- Dependencies: 13874 13875 13876 13877 13878 13879 13880 13881 13882 27
-- Name: news; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE news (
    news_id integer DEFAULT nextval(('verilion.news_news_id_seq'::text)::regclass) NOT NULL,
    news_title character varying(255) DEFAULT ''::character varying NOT NULL,
    news_teaser_text text DEFAULT ''::text NOT NULL,
    news_body_text text DEFAULT ''::text NOT NULL,
    news_start_date date DEFAULT ('now'::text)::date NOT NULL,
    news_end_date date DEFAULT '9999-12-31'::date NOT NULL,
    news_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    news_author_id integer DEFAULT 0 NOT NULL,
    news_categories_id integer,
    CONSTRAINT ck_news_active_yn CHECK (((news_active_yn = 'y'::bpchar) OR (news_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11872 (class 1259 OID 34749)
-- Dependencies: 13883 13884 13885 13886 27
-- Name: news_categories; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE news_categories (
    news_categories_id integer DEFAULT nextval(('verilion.news_categories_id_seq'::text)::regclass) NOT NULL,
    news_categories_name character varying(255) DEFAULT ''::character varying NOT NULL,
    news_categories_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_news_categories_active_yn CHECK (((news_categories_active_yn = 'y'::bpchar) OR (news_categories_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11873 (class 1259 OID 34755)
-- Dependencies: 27
-- Name: news_categories_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE news_categories_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11874 (class 1259 OID 34757)
-- Dependencies: 27
-- Name: news_news_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE news_news_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11875 (class 1259 OID 34759)
-- Dependencies: 27
-- Name: news_ticker; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE news_ticker (
    news_ticker_id integer NOT NULL,
    news_id bigint
);


--
-- TOC entry 11876 (class 1259 OID 34761)
-- Dependencies: 11875 27
-- Name: news_ticker_news_ticker_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE news_ticker_news_ticker_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14349 (class 0 OID 0)
-- Dependencies: 11876
-- Name: news_ticker_news_ticker_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE news_ticker_news_ticker_id_seq OWNED BY news_ticker.news_ticker_id;


--
-- TOC entry 11877 (class 1259 OID 34763)
-- Dependencies: 27
-- Name: orders; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE orders (
    order_id integer NOT NULL,
    customer_id bigint,
    order_status smallint,
    shipping_id integer,
    order_tracking_number character varying(255),
    ship_date timestamp without time zone,
    order_date timestamp without time zone,
    payment_method integer,
    credit_card_number character varying(25),
    credit_card_expiry character varying(10),
    credit_card_name_on_card character varying(255),
    order_shipping double precision,
    shipping_name character varying(255),
    shipping_address_1 character varying(255),
    shipping_address_2 character varying(255),
    shipping_town_city character varying(255),
    ct_province_state_id bigint,
    ct_country_id bigint,
    shipping_postal_code character varying(25),
    cancel_date timestamp without time zone
);


--
-- TOC entry 11878 (class 1259 OID 34768)
-- Dependencies: 27
-- Name: orders_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE orders_detail (
    orders_detail_id integer NOT NULL,
    order_id bigint,
    item_id bigint,
    quantity bigint,
    price double precision,
    tax double precision,
    order_detail_status integer,
    ship_date timestamp without time zone,
    cancel_date timestamp without time zone
);


--
-- TOC entry 11879 (class 1259 OID 34770)
-- Dependencies: 11878 27
-- Name: orders_detail_orders_detail_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE orders_detail_orders_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14350 (class 0 OID 0)
-- Dependencies: 11879
-- Name: orders_detail_orders_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE orders_detail_orders_detail_id_seq OWNED BY orders_detail.orders_detail_id;


--
-- TOC entry 11880 (class 1259 OID 34772)
-- Dependencies: 11877 27
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14351 (class 0 OID 0)
-- Dependencies: 11880
-- Name: orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE orders_order_id_seq OWNED BY orders.order_id;


SET default_with_oids = true;

--
-- TOC entry 11881 (class 1259 OID 34774)
-- Dependencies: 13890 13891 13892 13893 13894 13895 13896 13897 13898 13899 13900 13901 13902 27
-- Name: page; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page (
    page_id integer DEFAULT nextval(('verilion.page_page_id_seq'::text)::regclass) NOT NULL,
    page_name character varying(255) DEFAULT ''::character varying NOT NULL,
    template_id integer DEFAULT 0,
    page_access_level smallint DEFAULT (0)::smallint NOT NULL,
    page_invocation character varying(255) DEFAULT ''::character varying NOT NULL,
    page_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    display_in_iframe_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    iframe_url character varying(255) DEFAULT ''::character varying NOT NULL,
    page_secure_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    external_template character varying(512),
    page_immutable character(1) DEFAULT 'n'::bpchar,
    can_link_yn character(1) DEFAULT 'y'::bpchar,
    CONSTRAINT ck_page_active_yn CHECK (((page_active_yn = 'y'::bpchar) OR (page_active_yn = 'n'::bpchar))),
    CONSTRAINT ck_page_secure_yn CHECK (((page_secure_yn = 'y'::bpchar) OR (page_secure_yn = 'n'::bpchar)))
);


--
-- TOC entry 11882 (class 1259 OID 34792)
-- Dependencies: 13903 13904 13905 13906 13907 13908 13909 27
-- Name: page_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page_detail (
    page_detail_id integer DEFAULT nextval(('verilion.page_detail_page_detail_id_s'::text)::regclass) NOT NULL,
    page_id integer DEFAULT 0 NOT NULL,
    page_detail_title character varying(255) DEFAULT ''::character varying NOT NULL,
    page_detail_contents text DEFAULT ''::text NOT NULL,
    ct_language_id integer DEFAULT 1 NOT NULL,
    page_detail_title_style_id integer DEFAULT 0 NOT NULL,
    page_detail_contents_style_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11883 (class 1259 OID 34804)
-- Dependencies: 27
-- Name: page_detail_page_detail_id_s; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_detail_page_detail_id_s
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11884 (class 1259 OID 34806)
-- Dependencies: 13911 27
-- Name: page_group; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page_group (
    page_group_id integer NOT NULL,
    page_group_name character varying(255),
    page_group_description text,
    page_group_active_yn character(1) DEFAULT 'y'::bpchar
);


--
-- TOC entry 11885 (class 1259 OID 34812)
-- Dependencies: 27
-- Name: page_group_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page_group_detail (
    page_group_detail_id integer NOT NULL,
    page_group_id integer,
    page_id integer
);


--
-- TOC entry 11886 (class 1259 OID 34814)
-- Dependencies: 11885 27
-- Name: page_group_detail_page_group_detail_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_group_detail_page_group_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14352 (class 0 OID 0)
-- Dependencies: 11886
-- Name: page_group_detail_page_group_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE page_group_detail_page_group_detail_id_seq OWNED BY page_group_detail.page_group_detail_id;


--
-- TOC entry 11887 (class 1259 OID 34816)
-- Dependencies: 11884 27
-- Name: page_group_page_group_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_group_page_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14353 (class 0 OID 0)
-- Dependencies: 11887
-- Name: page_group_page_group_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE page_group_page_group_id_seq OWNED BY page_group.page_group_id;


--
-- TOC entry 11888 (class 1259 OID 34818)
-- Dependencies: 13914 27
-- Name: page_history; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page_history (
    page_history_id integer NOT NULL,
    page_id integer,
    page_contents text,
    date_time timestamp without time zone DEFAULT now()
);


--
-- TOC entry 11889 (class 1259 OID 34824)
-- Dependencies: 11888 27
-- Name: page_history_page_history_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_history_page_history_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14354 (class 0 OID 0)
-- Dependencies: 11889
-- Name: page_history_page_history_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE page_history_page_history_id_seq OWNED BY page_history.page_history_id;


--
-- TOC entry 11890 (class 1259 OID 34826)
-- Dependencies: 27
-- Name: page_page_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_page_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11891 (class 1259 OID 34828)
-- Dependencies: 27
-- Name: page_role; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE page_role (
    page_role_id integer NOT NULL,
    page_id integer,
    role_id integer
);


--
-- TOC entry 11892 (class 1259 OID 34830)
-- Dependencies: 11891 27
-- Name: page_role_page_role_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE page_role_page_role_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14355 (class 0 OID 0)
-- Dependencies: 11892
-- Name: page_role_page_role_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE page_role_page_role_id_seq OWNED BY page_role.page_role_id;


--
-- TOC entry 11893 (class 1259 OID 34832)
-- Dependencies: 13917 13918 27
-- Name: pmc_stories; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE pmc_stories (
    story_id integer NOT NULL,
    title character varying(255),
    datetime timestamp without time zone,
    story text,
    number_comments integer,
    counter bigint,
    story_topic_id integer,
    posted_by character varying(255),
    story_active_yn character(1) DEFAULT 'y'::bpchar,
    customer_id bigint DEFAULT 0
);


--
-- TOC entry 11894 (class 1259 OID 34839)
-- Dependencies: 13920 27
-- Name: pmc_stories_comments; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE pmc_stories_comments (
    comment_id integer NOT NULL,
    username character varying(255),
    story_id bigint,
    datetime timestamp without time zone,
    subject character varying(255),
    story_comment text,
    comment_active_yn character(1) DEFAULT 'y'::bpchar
);


--
-- TOC entry 11895 (class 1259 OID 34845)
-- Dependencies: 11894 27
-- Name: pmc_stories_comments_comment_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE pmc_stories_comments_comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14356 (class 0 OID 0)
-- Dependencies: 11895
-- Name: pmc_stories_comments_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE pmc_stories_comments_comment_id_seq OWNED BY pmc_stories_comments.comment_id;


--
-- TOC entry 11896 (class 1259 OID 34847)
-- Dependencies: 11893 27
-- Name: pmc_stories_story_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE pmc_stories_story_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14357 (class 0 OID 0)
-- Dependencies: 11896
-- Name: pmc_stories_story_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE pmc_stories_story_id_seq OWNED BY pmc_stories.story_id;


--
-- TOC entry 11897 (class 1259 OID 34849)
-- Dependencies: 13922 27
-- Name: pmc_story_category; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE pmc_story_category (
    story_category_id integer NOT NULL,
    story_category_name character varying(255),
    story_category_active_yn character(1) DEFAULT 'n'::bpchar,
    category_text character varying(255)
);


--
-- TOC entry 11898 (class 1259 OID 34855)
-- Dependencies: 11897 27
-- Name: pmc_story_category_story_category_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE pmc_story_category_story_category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14358 (class 0 OID 0)
-- Dependencies: 11898
-- Name: pmc_story_category_story_category_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE pmc_story_category_story_category_id_seq OWNED BY pmc_story_category.story_category_id;


--
-- TOC entry 11899 (class 1259 OID 34857)
-- Dependencies: 13924 13925 27
-- Name: polls; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE polls (
    poll_id integer NOT NULL,
    title character varying(255),
    date_time timestamp without time zone DEFAULT now(),
    number_voters integer,
    active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11900 (class 1259 OID 34861)
-- Dependencies: 27
-- Name: polls_check; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE polls_check (
    polls_check_id integer NOT NULL,
    poll_id integer,
    ip_address character varying(255)
);


--
-- TOC entry 11901 (class 1259 OID 34863)
-- Dependencies: 11900 27
-- Name: polls_check_polls_check_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE polls_check_polls_check_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14359 (class 0 OID 0)
-- Dependencies: 11901
-- Name: polls_check_polls_check_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE polls_check_polls_check_id_seq OWNED BY polls_check.polls_check_id;


--
-- TOC entry 11902 (class 1259 OID 34865)
-- Dependencies: 13928 27
-- Name: polls_data; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE polls_data (
    polls_data_id integer NOT NULL,
    poll_id integer,
    poll_option_text character varying,
    poll_option_count integer DEFAULT 0
);


--
-- TOC entry 11903 (class 1259 OID 34871)
-- Dependencies: 11902 27
-- Name: polls_data_polls_data_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE polls_data_polls_data_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14360 (class 0 OID 0)
-- Dependencies: 11903
-- Name: polls_data_polls_data_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE polls_data_polls_data_id_seq OWNED BY polls_data.polls_data_id;


--
-- TOC entry 11904 (class 1259 OID 34873)
-- Dependencies: 11899 27
-- Name: polls_poll_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE polls_poll_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14361 (class 0 OID 0)
-- Dependencies: 11904
-- Name: polls_poll_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE polls_poll_id_seq OWNED BY polls.poll_id;


--
-- TOC entry 11905 (class 1259 OID 34875)
-- Dependencies: 13930 27
-- Name: promotions; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE promotions (
    promotion_id integer NOT NULL,
    promotion_name character varying(255),
    discount_rate real DEFAULT 0
);


--
-- TOC entry 11906 (class 1259 OID 34878)
-- Dependencies: 11905 27
-- Name: promotions_promotion_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE promotions_promotion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14362 (class 0 OID 0)
-- Dependencies: 11906
-- Name: promotions_promotion_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE promotions_promotion_id_seq OWNED BY promotions.promotion_id;


SET default_with_oids = true;

--
-- TOC entry 11907 (class 1259 OID 34880)
-- Dependencies: 13931 13932 13933 13934 27
-- Name: quotes; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE quotes (
    quote_id integer DEFAULT nextval(('verilion.quotes_seq'::text)::regclass) NOT NULL,
    quote_text character varying(255) DEFAULT ''::character varying NOT NULL,
    quote_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    quote_name character varying(255),
    CONSTRAINT ck_quote_active_yn CHECK (((quote_active_yn = 'y'::bpchar) OR (quote_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11908 (class 1259 OID 34889)
-- Dependencies: 27
-- Name: quotes_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE quotes_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11909 (class 1259 OID 34891)
-- Dependencies: 13935 13936 13937 27
-- Name: role; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE "role" (
    role_id bigint DEFAULT nextval(('verilion.role_id_seq'::text)::regclass) NOT NULL,
    role_name character varying(25) NOT NULL,
    role_description character varying(255),
    role_active_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_role_active_yn CHECK (((role_active_yn = 'y'::bpchar) OR (role_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11910 (class 1259 OID 34896)
-- Dependencies: 27
-- Name: role_assigned_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE role_assigned_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11911 (class 1259 OID 34898)
-- Dependencies: 27
-- Name: role_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE role_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = false;

--
-- TOC entry 11912 (class 1259 OID 34900)
-- Dependencies: 13939 27
-- Name: servers; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE servers (
    server_id integer NOT NULL,
    server_name character varying(512),
    port bigint,
    server_description text,
    server_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11913 (class 1259 OID 34906)
-- Dependencies: 11912 27
-- Name: servers_server_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE servers_server_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14363 (class 0 OID 0)
-- Dependencies: 11913
-- Name: servers_server_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE servers_server_id_seq OWNED BY servers.server_id;


--
-- TOC entry 11914 (class 1259 OID 34908)
-- Dependencies: 27
-- Name: sessions; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE sessions (
    session_id integer NOT NULL,
    sessions integer
);


--
-- TOC entry 11915 (class 1259 OID 34910)
-- Dependencies: 11914 27
-- Name: sessions_session_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE sessions_session_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14364 (class 0 OID 0)
-- Dependencies: 11915
-- Name: sessions_session_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE sessions_session_id_seq OWNED BY sessions.session_id;


--
-- TOC entry 11916 (class 1259 OID 34912)
-- Dependencies: 13942 27
-- Name: shipping; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE shipping (
    shipping_id integer NOT NULL,
    shipping_name character varying(255),
    shipping_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11917 (class 1259 OID 34915)
-- Dependencies: 13944 27
-- Name: shipping_rate_method; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE shipping_rate_method (
    shipping_rate_method_id integer NOT NULL,
    shipping_rate_method_name character varying(255),
    shipping_rate_method_active_yn character(1) DEFAULT 'n'::bpchar
);


--
-- TOC entry 11918 (class 1259 OID 34918)
-- Dependencies: 11917 27
-- Name: shipping_rate_method_shipping_rate_method_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE shipping_rate_method_shipping_rate_method_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14365 (class 0 OID 0)
-- Dependencies: 11918
-- Name: shipping_rate_method_shipping_rate_method_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE shipping_rate_method_shipping_rate_method_id_seq OWNED BY shipping_rate_method.shipping_rate_method_id;


--
-- TOC entry 11919 (class 1259 OID 34920)
-- Dependencies: 11916 27
-- Name: shipping_shipping_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE shipping_shipping_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14366 (class 0 OID 0)
-- Dependencies: 11919
-- Name: shipping_shipping_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE shipping_shipping_id_seq OWNED BY shipping.shipping_id;


--
-- TOC entry 11920 (class 1259 OID 34922)
-- Dependencies: 27
-- Name: sire_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE sire_id_seq
    START WITH 6
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11921 (class 1259 OID 34924)
-- Dependencies: 27
-- Name: species_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE species_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_with_oids = true;

--
-- TOC entry 11922 (class 1259 OID 34926)
-- Dependencies: 13945 13946 13947 13948 13949 13950 13951 13952 13953 13954 27
-- Name: style; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE style (
    style_id integer DEFAULT nextval(('style_style_id_seq'::text)::regclass) NOT NULL,
    style_name character varying(255) DEFAULT ''::character varying NOT NULL,
    font_family character varying(255) DEFAULT 'Verdana, Arial, Helvetica, sans-serif'::character varying NOT NULL,
    font_size integer DEFAULT 11 NOT NULL,
    font_style character varying(255) DEFAULT 'normal'::character varying NOT NULL,
    line_height integer DEFAULT 22 NOT NULL,
    font_weight character varying(255) DEFAULT 'normal'::character varying NOT NULL,
    font_variant character varying(255) DEFAULT 'normal'::character varying NOT NULL,
    text_decoration character varying(255) DEFAULT 'none'::character varying NOT NULL,
    color character varying(7) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11923 (class 1259 OID 34941)
-- Dependencies: 27
-- Name: style_style_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE style_style_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11924 (class 1259 OID 34943)
-- Dependencies: 13955 13956 27
-- Name: system_messages; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE system_messages (
    system_message_id integer DEFAULT nextval(('verilion.system_messages_system_messa'::text)::regclass) NOT NULL,
    system_message_name character varying(255) DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 11925 (class 1259 OID 34947)
-- Dependencies: 13957 13958 13959 13960 13961 27
-- Name: system_messages_detail; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE system_messages_detail (
    system_messages_detail_id integer DEFAULT nextval(('verilion.system_messages_detail_syste'::text)::regclass) NOT NULL,
    system_message_id integer DEFAULT 0 NOT NULL,
    system_messages_detail_display_name character varying(255) DEFAULT ''::character varying NOT NULL,
    system_messages_detail_message text DEFAULT ''::text NOT NULL,
    ct_language_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11926 (class 1259 OID 34957)
-- Dependencies: 27
-- Name: system_messages_detail_syste; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE system_messages_detail_syste
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11927 (class 1259 OID 34959)
-- Dependencies: 27
-- Name: system_messages_system_messa; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE system_messages_system_messa
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11928 (class 1259 OID 34961)
-- Dependencies: 13962 13963 13964 13965 27
-- Name: system_pages; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE system_pages (
    system_page_id integer DEFAULT nextval(('verilion.system_pages_system_page_id_'::text)::regclass) NOT NULL,
    system_page_name character varying(255) DEFAULT ''::character varying NOT NULL,
    system_page_contents text DEFAULT ''::text NOT NULL,
    ct_access_level_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 11929 (class 1259 OID 34970)
-- Dependencies: 27
-- Name: system_pages_system_page_id_; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE system_pages_system_page_id_
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11930 (class 1259 OID 34972)
-- Dependencies: 13966 13967 13968 13969 13970 13971 13972 13973 13974 13975 13976 13977 13978 27
-- Name: system_preferences; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE system_preferences (
    system_online_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    use_sef_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    template_path character varying(255) DEFAULT ''::character varying NOT NULL,
    mailhost character varying(255) DEFAULT ''::character varying NOT NULL,
    secure_port character varying(255) DEFAULT '443'::character varying NOT NULL,
    insecure_port character varying(255) DEFAULT '80'::character varying NOT NULL,
    "database" character(1) DEFAULT 'p'::bpchar NOT NULL,
    upload_path character varying(255) DEFAULT ''::character varying NOT NULL,
    host_name character varying(255) DEFAULT 'www.skylos.com'::character varying NOT NULL,
    template_directory character varying(255) DEFAULT ''::character varying NOT NULL,
    homepage_page_id integer DEFAULT 1 NOT NULL,
    homepage character varying(255),
    homepage_sef character varying(255),
    site_description character varying(255),
    memory_threshold character varying(20),
    admin_email character varying(255),
    session_timeout bigint DEFAULT 1805,
    cache_timeout integer,
    system_path character varying(255),
    store_max_subcategories integer DEFAULT 3
);


SET default_with_oids = false;

--
-- TOC entry 11931 (class 1259 OID 34990)
-- Dependencies: 13980 27
-- Name: tax; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE tax (
    tax_id integer NOT NULL,
    tax_name character varying(255),
    tax_rate_1 real,
    tax_rate_2 real,
    taxes_cumulative_yn character(1) DEFAULT 'n'::bpchar,
    ct_province_state_id bigint,
    ct_country_id bigint,
    tax_rate_1_desc character varying(255),
    tax_rate_2_desc character varying(255)
);


--
-- TOC entry 11932 (class 1259 OID 34996)
-- Dependencies: 11931 27
-- Name: tax_tax_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE tax_tax_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14367 (class 0 OID 0)
-- Dependencies: 11932
-- Name: tax_tax_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE tax_tax_id_seq OWNED BY tax.tax_id;


SET default_with_oids = true;

--
-- TOC entry 11933 (class 1259 OID 34998)
-- Dependencies: 13981 13982 13983 13984 13985 13986 13987 27
-- Name: template; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE "template" (
    template_id integer DEFAULT nextval(('template_template_id_seq'::text)::regclass) NOT NULL,
    template_name character varying(255) DEFAULT ''::character varying NOT NULL,
    template_contents text DEFAULT ''::text NOT NULL,
    template_active_yn character(1) DEFAULT 'y'::bpchar NOT NULL,
    system_template_yn character(1) DEFAULT 'n'::bpchar NOT NULL,
    CONSTRAINT ck_system_template_yn CHECK (((system_template_yn = 'y'::bpchar) OR (system_template_yn = 'n'::bpchar))),
    CONSTRAINT ck_template_active_yn CHECK (((template_active_yn = 'y'::bpchar) OR (template_active_yn = 'n'::bpchar)))
);


--
-- TOC entry 11934 (class 1259 OID 35010)
-- Dependencies: 27
-- Name: template_template_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE template_template_id_seq
    START WITH 6
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11935 (class 1259 OID 35012)
-- Dependencies: 13988 13989 13990 13991 27
-- Name: tree_menu; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE tree_menu (
    tree_menu_id integer DEFAULT nextval(('tree_menu_id_seq'::text)::regclass) NOT NULL,
    tree_menu_var_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_display_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_link character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_name character varying(255),
    tree_menu_tag character varying(255),
    tree_menu_active_yn character(1)
);


--
-- TOC entry 11936 (class 1259 OID 35021)
-- Dependencies: 27
-- Name: tree_menu_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE tree_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11937 (class 1259 OID 35023)
-- Dependencies: 13992 13993 13994 13995 13996 27
-- Name: tree_menu_level_one; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE tree_menu_level_one (
    tree_menu_level_one_id integer DEFAULT nextval(('tree_menu_level_one_id'::text)::regclass) NOT NULL,
    tree_menu_var_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_one_var_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_one_display_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_one_link character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_one_active_yn character(1),
    tree_menu_id integer
);


--
-- TOC entry 11938 (class 1259 OID 35033)
-- Dependencies: 27
-- Name: tree_menu_level_one_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE tree_menu_level_one_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11939 (class 1259 OID 35035)
-- Dependencies: 13997 13998 13999 14000 14001 27
-- Name: tree_menu_level_two; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE tree_menu_level_two (
    tree_menu_level_two_id integer DEFAULT nextval(('tree_menu_level_two_id_seq'::text)::regclass) NOT NULL,
    tree_menu_level_two_var character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_two_destination character(1) DEFAULT 'S'::bpchar NOT NULL,
    tree_menu_level_two_display_name character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_two_link character varying(255) DEFAULT ''::character varying NOT NULL,
    tree_menu_level_one_id integer,
    tree_menu_level_two_active_yn character(1)
);


--
-- TOC entry 11940 (class 1259 OID 35045)
-- Dependencies: 27
-- Name: tree_menu_level_two_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE tree_menu_level_two_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 11941 (class 1259 OID 35047)
-- Dependencies: 14002 27
-- Name: turba_objects; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE turba_objects (
    object_id character varying(32) NOT NULL,
    owner_id character varying(255) NOT NULL,
    object_type character varying(255) DEFAULT 'Object'::character varying NOT NULL,
    object_members character varying(4096),
    object_name character varying(255),
    object_alias character varying(32),
    object_email character varying(255),
    object_homeaddress character varying(255),
    object_workaddress character varying(255),
    object_homephone character varying(25),
    object_workphone character varying(25),
    object_cellphone character varying(25),
    object_fax character varying(25),
    object_title character varying(255),
    object_company character varying(255),
    object_notes text
);


SET default_with_oids = false;

--
-- TOC entry 11942 (class 1259 OID 35053)
-- Dependencies: 27
-- Name: user_role; Type: TABLE; Schema: verilion; Owner: -; Tablespace: 
--

CREATE TABLE user_role (
    user_role_id integer NOT NULL,
    role_id bigint,
    customer_id bigint
);


--
-- TOC entry 11943 (class 1259 OID 35055)
-- Dependencies: 11942 27
-- Name: user_role_user_role_id_seq; Type: SEQUENCE; Schema: verilion; Owner: -
--

CREATE SEQUENCE user_role_user_role_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 14369 (class 0 OID 0)
-- Dependencies: 11943
-- Name: user_role_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: verilion; Owner: -
--

ALTER SEQUENCE user_role_user_role_id_seq OWNED BY user_role.user_role_id;


--
-- TOC entry 11944 (class 1259 OID 35057)
-- Dependencies: 13318 27
-- Name: v_admins; Type: VIEW; Schema: verilion; Owner: -
--

CREATE VIEW v_admins AS
    SELECT c.customer_id, c.customer_first_name AS admin_first_name, c.customer_last_name AS admin_last_name, ced.customer_email AS admin_email, c.customer_password AS admin_password FROM (customer c JOIN customer_email_detail ced ON (((c.customer_id = ced.customer_id) AND (ced.customer_email_default_yn = 'y'::bpchar)))) WHERE (c.customer_access_level = 3);


--
-- TOC entry 11945 (class 1259 OID 35060)
-- Dependencies: 13319 27
-- Name: v_buyers; Type: VIEW; Schema: verilion; Owner: -
--

CREATE VIEW v_buyers AS
    SELECT c.customer_id, ced.customer_email, c.customer_password FROM (customer c JOIN customer_email_detail ced ON (((c.customer_id = ced.customer_id) AND (ced.customer_email_default_yn = 'y'::bpchar)))) WHERE (c.customer_access_level = 1);


--
-- TOC entry 11946 (class 1259 OID 35063)
-- Dependencies: 13320 27
-- Name: v_customer; Type: VIEW; Schema: verilion; Owner: -
--

CREATE VIEW v_customer AS
    SELECT c.customer_id, c.username, c.ct_salutation_id, cts.ct_salutation_name, c.customer_last_name, c.customer_first_name, c.customer_company_name, ced.customer_email, c.customer_password, c.customer_website_url, c.image_id, c.customer_isnew_yn, cad.customer_address, cad.customer_town_city, cad.ct_province_state_id, cp.ct_province_state_name, cad.customer_zip_postal, cad.ct_country_id, cc.ct_country_name, cadb.customer_address AS billing_address, cadb.customer_town_city AS billing_city, cadb.ct_province_state_id AS billing_province_id, cpb.ct_province_state_name AS billing_province, cadb.customer_zip_postal AS billing_postal, cadb.ct_country_id AS billing_country_id, ccb.ct_country_name AS billing_country_name, cpd.ct_phone_type_id, cpd.customer_phone, cpd.customer_phone_extension AS extension, CASE WHEN ((cpdf.customer_phone)::text = ''::text) THEN 'no fax'::character varying WHEN ((cpdf.customer_phone)::text = NULL::text) THEN 'no fax'::character varying ELSE cpdf.customer_phone END AS fax, c.customer_add_to_mailing_list, c.ct_term_id, ctt.ct_term_name, c.ct_credit_card_id, ctc.ct_credit_card_name, c.customer_credit_card_number, c.customer_card_expiry_month, c.customer_credit_card_expiry_year, c.customer_name_on_card, c.customer_registration_date, c.customer_next_billing_date, c.customer_active_yn, c.customer_access_level, c.customer_company_description, c.ct_package_id, ct_pk.ct_package_name, ct_al.ct_access_level_name FROM ((((((((((((((customer c JOIN customer_address_detail cad ON (((c.customer_id = cad.customer_id) AND (cad.primary_address_yn = 'y'::bpchar)))) JOIN ct_province_state cp ON ((cad.ct_province_state_id = cp.ct_province_state_id))) JOIN ct_country cc ON ((cad.ct_country_id = cc.ct_country_id))) JOIN customer_address_detail cadb ON (((c.customer_id = cadb.customer_id) AND (cadb.ct_address_type_id = 3)))) JOIN ct_province_state cpb ON ((cadb.ct_province_state_id = cpb.ct_province_state_id))) JOIN ct_country ccb ON ((cadb.ct_country_id = ccb.ct_country_id))) JOIN ct_salutation cts ON ((c.ct_salutation_id = cts.ct_salutation_id))) JOIN ct_credit_card ctc ON ((c.ct_credit_card_id = ctc.ct_credit_card_id))) JOIN customer_phone_detail cpd ON (((c.customer_id = cpd.customer_id) AND (cpd.customer_phone_default_yn = 'y'::bpchar)))) JOIN customer_phone_detail cpdf ON (((c.customer_id = cpdf.customer_id) AND (cpdf.ct_phone_type_id = 4)))) JOIN customer_email_detail ced ON (((c.customer_id = ced.customer_id) AND (ced.customer_email_default_yn = 'y'::bpchar)))) JOIN ct_term ctt ON ((c.ct_term_id = ctt.ct_term_id))) JOIN ct_packages ct_pk ON ((c.ct_package_id = ct_pk.ct_package_id))) JOIN ct_access_level ct_al ON ((c.customer_access_level = ct_al.ct_access_level_id))) ORDER BY c.customer_last_name, c.customer_first_name;


--
-- TOC entry 11947 (class 1259 OID 35067)
-- Dependencies: 13321 27
-- Name: v_province_state; Type: VIEW; Schema: verilion; Owner: -
--

CREATE VIEW v_province_state AS
    SELECT ct_province_state.ct_province_state_id, ct_province_state.ct_province_state_name, ct_province_state.ct_province_state_abb, CASE WHEN (ct_province_state.ct_province_state_id < 14) THEN '1'::text ELSE '2'::text END AS ct_country_id FROM pmc.ct_province_state ORDER BY CASE WHEN (ct_province_state.ct_province_state_id < 14) THEN '1'::text ELSE '2'::text END, ct_province_state.ct_province_state_name;


--
-- TOC entry 11948 (class 1259 OID 35070)
-- Dependencies: 13322 27
-- Name: v_user_roles; Type: VIEW; Schema: verilion; Owner: -
--

CREATE VIEW v_user_roles AS
    SELECT c.customer_last_name, c.customer_first_name, c.customer_id, r.role_id, r.role_name, ur.user_role_id FROM ((customer c JOIN user_role ur ON ((ur.customer_id = c.customer_id))) JOIN "role" r ON ((r.role_id = ur.role_id)));


--
-- TOC entry 13655 (class 2604 OID 38286)
-- Dependencies: 11744 11743
-- Name: admin_page_group_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE admin_page_group ALTER COLUMN admin_page_group_id SET DEFAULT nextval('admin_page_group_admin_page_group_id_seq'::regclass);


--
-- TOC entry 13657 (class 2604 OID 38287)
-- Dependencies: 11746 11745
-- Name: admin_page_group_detail_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE admin_page_group_detail ALTER COLUMN admin_page_group_detail_id SET DEFAULT nextval('admin_page_group_detail_admin_page_group_detail_id_seq'::regclass);


--
-- TOC entry 13658 (class 2604 OID 38288)
-- Dependencies: 11748 11747
-- Name: admin_page_group_role_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE admin_page_group_role ALTER COLUMN admin_page_group_role_id SET DEFAULT nextval('admin_page_group_role_admin_page_group_role_id_seq'::regclass);


--
-- TOC entry 13659 (class 2604 OID 38289)
-- Dependencies: 11751 11750
-- Name: admin_page_role_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE admin_page_role ALTER COLUMN admin_page_role_id SET DEFAULT nextval('admin_page_role_admin_page_role_id_seq'::regclass);


--
-- TOC entry 13680 (class 2604 OID 38290)
-- Dependencies: 11758 11757
-- Name: banner_clicks_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE banner_clicks ALTER COLUMN banner_clicks_id SET DEFAULT nextval('banner_clicks_banner_clicks_id_seq'::regclass);


--
-- TOC entry 13682 (class 2604 OID 38291)
-- Dependencies: 11760 11759
-- Name: banner_impressions_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE banner_impressions ALTER COLUMN banner_impressions_id SET DEFAULT nextval('banner_impressions_banner_impressions_id_seq'::regclass);


--
-- TOC entry 13684 (class 2604 OID 38292)
-- Dependencies: 11762 11761
-- Name: banner_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE banners ALTER COLUMN banner_id SET DEFAULT nextval('banners_banner_id_seq'::regclass);


--
-- TOC entry 13686 (class 2604 OID 38293)
-- Dependencies: 11766 11765
-- Name: career_locations_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE career_locations ALTER COLUMN career_locations_id SET DEFAULT nextval('career_locations_career_locations_id_seq'::regclass);


--
-- TOC entry 13687 (class 2604 OID 38294)
-- Dependencies: 11768 11767
-- Name: career_type_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE career_type ALTER COLUMN career_type_id SET DEFAULT nextval('career_type_career_type_id_seq'::regclass);


--
-- TOC entry 13688 (class 2604 OID 38295)
-- Dependencies: 11770 11769
-- Name: career_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE careers ALTER COLUMN career_id SET DEFAULT nextval('careers_career_id_seq'::regclass);


--
-- TOC entry 13689 (class 2604 OID 38296)
-- Dependencies: 11772 11771
-- Name: category_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE category ALTER COLUMN category_id SET DEFAULT nextval('category_category_id_seq'::regclass);


--
-- TOC entry 13692 (class 2604 OID 38297)
-- Dependencies: 11776 11775
-- Name: component_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE components ALTER COLUMN component_id SET DEFAULT nextval('components_component_id_seq'::regclass);


--
-- TOC entry 13699 (class 2604 OID 38298)
-- Dependencies: 11782 11781
-- Name: ct_banner_position_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE ct_banner_position ALTER COLUMN ct_banner_position_id SET DEFAULT nextval('ct_banner_position_ct_banner_position_id_seq'::regclass);


--
-- TOC entry 13700 (class 2604 OID 38299)
-- Dependencies: 11784 11783
-- Name: ct_banner_sizes_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE ct_banner_sizes ALTER COLUMN ct_banner_sizes_id SET DEFAULT nextval('ct_banner_sizes_ct_banner_sizes_id_seq'::regclass);


--
-- TOC entry 13731 (class 2604 OID 38300)
-- Dependencies: 11808 11807
-- Name: currency_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE currency ALTER COLUMN currency_id SET DEFAULT nextval('currency_currency_id_seq'::regclass);


--
-- TOC entry 13776 (class 2604 OID 38301)
-- Dependencies: 11817 11816
-- Name: customer_message_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE customer_messages ALTER COLUMN customer_message_id SET DEFAULT nextval('customer_messages_customer_message_id_seq'::regclass);


--
-- TOC entry 13781 (class 2604 OID 38302)
-- Dependencies: 11819 11818
-- Name: customer_message_sent_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE customer_messages_sent ALTER COLUMN customer_message_sent_id SET DEFAULT nextval('customer_messages_sent_customer_message_sent_id_seq'::regclass);


--
-- TOC entry 13799 (class 2604 OID 38303)
-- Dependencies: 11827 11826
-- Name: event_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE events ALTER COLUMN event_id SET DEFAULT nextval('events_event_id_seq'::regclass);


--
-- TOC entry 13801 (class 2604 OID 38304)
-- Dependencies: 11831 11828
-- Name: faq_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE faq ALTER COLUMN faq_id SET DEFAULT nextval('faq_faq_id_seq'::regclass);


--
-- TOC entry 13803 (class 2604 OID 38305)
-- Dependencies: 11830 11829
-- Name: faq_categories_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE faq_categories ALTER COLUMN faq_categories_id SET DEFAULT nextval('faq_categories_faq_categories_id_seq'::regclass);


--
-- TOC entry 13811 (class 2604 OID 38306)
-- Dependencies: 11841 11834
-- Name: item_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item ALTER COLUMN item_id SET DEFAULT nextval('item_item_id_seq'::regclass);


--
-- TOC entry 13814 (class 2604 OID 38307)
-- Dependencies: 11836 11835
-- Name: item_attributes_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item_attributes ALTER COLUMN item_attributes_id SET DEFAULT nextval('item_attributes_item_attributes_id_seq'::regclass);


--
-- TOC entry 13816 (class 2604 OID 38308)
-- Dependencies: 11838 11837
-- Name: item_category_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item_category ALTER COLUMN item_category_id SET DEFAULT nextval('item_category_item_category_id_seq'::regclass);


--
-- TOC entry 13817 (class 2604 OID 38309)
-- Dependencies: 11840 11839
-- Name: item_image_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item_images ALTER COLUMN item_image_id SET DEFAULT nextval('item_images_item_image_id_seq'::regclass);


--
-- TOC entry 13823 (class 2604 OID 38310)
-- Dependencies: 11843 11842
-- Name: item_shipping_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item_shipping ALTER COLUMN item_shipping_id SET DEFAULT nextval('item_shipping_item_shipping_id_seq'::regclass);


--
-- TOC entry 13824 (class 2604 OID 38311)
-- Dependencies: 11845 11844
-- Name: item_special_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE item_special ALTER COLUMN item_special_id SET DEFAULT nextval('item_special_item_special_id_seq'::regclass);


--
-- TOC entry 13826 (class 2604 OID 38312)
-- Dependencies: 11847 11846
-- Name: jsp_template_page_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE jsp_template_page ALTER COLUMN jsp_template_page_id SET DEFAULT nextval('jsp_template_page_jsp_template_page_id_seq'::regclass);


--
-- TOC entry 13827 (class 2604 OID 38313)
-- Dependencies: 11849 11848
-- Name: jsp_template_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE jsp_templates ALTER COLUMN jsp_template_id SET DEFAULT nextval('jsp_templates_jsp_template_id_seq'::regclass);


--
-- TOC entry 13829 (class 2604 OID 38314)
-- Dependencies: 11853 11850
-- Name: link_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE links ALTER COLUMN link_id SET DEFAULT nextval('links_link_id_seq'::regclass);


--
-- TOC entry 13831 (class 2604 OID 38315)
-- Dependencies: 11852 11851
-- Name: links_cat_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE links_categories ALTER COLUMN links_cat_id SET DEFAULT nextval('links_categories_links_cat_id_seq'::regclass);


--
-- TOC entry 13833 (class 2604 OID 38316)
-- Dependencies: 11856 11855
-- Name: logged_in_user_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE logged_in_users ALTER COLUMN logged_in_user_id SET DEFAULT nextval('logged_in_users_logged_in_user_id_seq'::regclass);


--
-- TOC entry 13862 (class 2604 OID 38317)
-- Dependencies: 11866 11865
-- Name: message_queue_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE message_queue ALTER COLUMN message_queue_id SET DEFAULT nextval('message_queue_message_queue_id_seq'::regclass);


--
-- TOC entry 13887 (class 2604 OID 38318)
-- Dependencies: 11876 11875
-- Name: news_ticker_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE news_ticker ALTER COLUMN news_ticker_id SET DEFAULT nextval('news_ticker_news_ticker_id_seq'::regclass);


--
-- TOC entry 13888 (class 2604 OID 38319)
-- Dependencies: 11880 11877
-- Name: order_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE orders ALTER COLUMN order_id SET DEFAULT nextval('orders_order_id_seq'::regclass);


--
-- TOC entry 13889 (class 2604 OID 38320)
-- Dependencies: 11879 11878
-- Name: orders_detail_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE orders_detail ALTER COLUMN orders_detail_id SET DEFAULT nextval('orders_detail_orders_detail_id_seq'::regclass);


--
-- TOC entry 13910 (class 2604 OID 38321)
-- Dependencies: 11887 11884
-- Name: page_group_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE page_group ALTER COLUMN page_group_id SET DEFAULT nextval('page_group_page_group_id_seq'::regclass);


--
-- TOC entry 13912 (class 2604 OID 38322)
-- Dependencies: 11886 11885
-- Name: page_group_detail_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE page_group_detail ALTER COLUMN page_group_detail_id SET DEFAULT nextval('page_group_detail_page_group_detail_id_seq'::regclass);


--
-- TOC entry 13913 (class 2604 OID 38323)
-- Dependencies: 11889 11888
-- Name: page_history_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE page_history ALTER COLUMN page_history_id SET DEFAULT nextval('page_history_page_history_id_seq'::regclass);


--
-- TOC entry 13915 (class 2604 OID 38324)
-- Dependencies: 11892 11891
-- Name: page_role_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE page_role ALTER COLUMN page_role_id SET DEFAULT nextval('page_role_page_role_id_seq'::regclass);


--
-- TOC entry 13916 (class 2604 OID 38325)
-- Dependencies: 11896 11893
-- Name: story_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE pmc_stories ALTER COLUMN story_id SET DEFAULT nextval('pmc_stories_story_id_seq'::regclass);


--
-- TOC entry 13919 (class 2604 OID 38326)
-- Dependencies: 11895 11894
-- Name: comment_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE pmc_stories_comments ALTER COLUMN comment_id SET DEFAULT nextval('pmc_stories_comments_comment_id_seq'::regclass);


--
-- TOC entry 13921 (class 2604 OID 38327)
-- Dependencies: 11898 11897
-- Name: story_category_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE pmc_story_category ALTER COLUMN story_category_id SET DEFAULT nextval('pmc_story_category_story_category_id_seq'::regclass);


--
-- TOC entry 13923 (class 2604 OID 38328)
-- Dependencies: 11904 11899
-- Name: poll_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE polls ALTER COLUMN poll_id SET DEFAULT nextval('polls_poll_id_seq'::regclass);


--
-- TOC entry 13926 (class 2604 OID 38329)
-- Dependencies: 11901 11900
-- Name: polls_check_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE polls_check ALTER COLUMN polls_check_id SET DEFAULT nextval('polls_check_polls_check_id_seq'::regclass);


--
-- TOC entry 13927 (class 2604 OID 38330)
-- Dependencies: 11903 11902
-- Name: polls_data_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE polls_data ALTER COLUMN polls_data_id SET DEFAULT nextval('polls_data_polls_data_id_seq'::regclass);


--
-- TOC entry 13929 (class 2604 OID 38331)
-- Dependencies: 11906 11905
-- Name: promotion_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE promotions ALTER COLUMN promotion_id SET DEFAULT nextval('promotions_promotion_id_seq'::regclass);


--
-- TOC entry 13938 (class 2604 OID 38332)
-- Dependencies: 11913 11912
-- Name: server_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE servers ALTER COLUMN server_id SET DEFAULT nextval('servers_server_id_seq'::regclass);


--
-- TOC entry 13940 (class 2604 OID 38333)
-- Dependencies: 11915 11914
-- Name: session_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE sessions ALTER COLUMN session_id SET DEFAULT nextval('sessions_session_id_seq'::regclass);


--
-- TOC entry 13941 (class 2604 OID 38334)
-- Dependencies: 11919 11916
-- Name: shipping_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE shipping ALTER COLUMN shipping_id SET DEFAULT nextval('shipping_shipping_id_seq'::regclass);


--
-- TOC entry 13943 (class 2604 OID 38335)
-- Dependencies: 11918 11917
-- Name: shipping_rate_method_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE shipping_rate_method ALTER COLUMN shipping_rate_method_id SET DEFAULT nextval('shipping_rate_method_shipping_rate_method_id_seq'::regclass);


--
-- TOC entry 13979 (class 2604 OID 38336)
-- Dependencies: 11932 11931
-- Name: tax_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE tax ALTER COLUMN tax_id SET DEFAULT nextval('tax_tax_id_seq'::regclass);


--
-- TOC entry 14003 (class 2604 OID 38337)
-- Dependencies: 11943 11942
-- Name: user_role_id; Type: DEFAULT; Schema: verilion; Owner: -
--

ALTER TABLE user_role ALTER COLUMN user_role_id SET DEFAULT nextval('user_role_user_role_id_seq'::regclass);


--
-- TOC entry 14007 (class 2606 OID 113427)
-- Dependencies: 11735 11735
-- Name: admin_menu_item_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_menu_item_detail
    ADD CONSTRAINT admin_menu_item_detail_pkey PRIMARY KEY (admin_menu_item_detail_id);


--
-- TOC entry 14012 (class 2606 OID 113429)
-- Dependencies: 11736 11736
-- Name: admin_menu_items_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_menu_items
    ADD CONSTRAINT admin_menu_items_pkey PRIMARY KEY (admin_menu_items_id);


--
-- TOC entry 14005 (class 2606 OID 113431)
-- Dependencies: 11734 11734
-- Name: admin_menu_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_menu
    ADD CONSTRAINT admin_menu_pkey PRIMARY KEY (admin_menu_id);


--
-- TOC entry 14017 (class 2606 OID 113433)
-- Dependencies: 11741 11741
-- Name: admin_page_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page_detail
    ADD CONSTRAINT admin_page_detail_pkey PRIMARY KEY (page_detail_id);


--
-- TOC entry 14021 (class 2606 OID 113435)
-- Dependencies: 11745 11745
-- Name: admin_page_group_detail_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page_group_detail
    ADD CONSTRAINT admin_page_group_detail_pk PRIMARY KEY (admin_page_group_detail_id);


--
-- TOC entry 14019 (class 2606 OID 113437)
-- Dependencies: 11743 11743
-- Name: admin_page_group_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page_group
    ADD CONSTRAINT admin_page_group_id_pk PRIMARY KEY (admin_page_group_id);


--
-- TOC entry 14023 (class 2606 OID 113439)
-- Dependencies: 11747 11747
-- Name: admin_page_group_role_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page_group_role
    ADD CONSTRAINT admin_page_group_role_id_pk PRIMARY KEY (admin_page_group_role_id);


--
-- TOC entry 14015 (class 2606 OID 113441)
-- Dependencies: 11740 11740
-- Name: admin_page_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page
    ADD CONSTRAINT admin_page_pkey PRIMARY KEY (page_id);


--
-- TOC entry 14025 (class 2606 OID 113443)
-- Dependencies: 11750 11750
-- Name: admin_page_role_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_page_role
    ADD CONSTRAINT admin_page_role_pk PRIMARY KEY (admin_page_role_id);


--
-- TOC entry 14029 (class 2606 OID 113445)
-- Dependencies: 11754 11754
-- Name: archive_page_entry_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY archive_page_entry
    ADD CONSTRAINT archive_page_entry_pkey PRIMARY KEY (archive_page_entry_id);


--
-- TOC entry 14027 (class 2606 OID 113447)
-- Dependencies: 11753 11753
-- Name: archive_page_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY archive_page
    ADD CONSTRAINT archive_page_pkey PRIMARY KEY (archive_page_id);


--
-- TOC entry 14035 (class 2606 OID 113449)
-- Dependencies: 11761 11761
-- Name: banner_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banners
    ADD CONSTRAINT banner_id_pk PRIMARY KEY (banner_id);


--
-- TOC entry 14031 (class 2606 OID 113451)
-- Dependencies: 11757 11757
-- Name: bcidpk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banner_clicks
    ADD CONSTRAINT bcidpk PRIMARY KEY (banner_clicks_id);


--
-- TOC entry 14033 (class 2606 OID 113453)
-- Dependencies: 11759 11759
-- Name: biidpk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banner_impressions
    ADD CONSTRAINT biidpk PRIMARY KEY (banner_impressions_id);


--
-- TOC entry 14037 (class 2606 OID 113455)
-- Dependencies: 11765 11765
-- Name: career_location_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY career_locations
    ADD CONSTRAINT career_location_pk PRIMARY KEY (career_locations_id);


--
-- TOC entry 14039 (class 2606 OID 113457)
-- Dependencies: 11767 11767
-- Name: career_type_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY career_type
    ADD CONSTRAINT career_type_pk PRIMARY KEY (career_type_id);


--
-- TOC entry 14041 (class 2606 OID 113459)
-- Dependencies: 11769 11769
-- Name: careers_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY careers
    ADD CONSTRAINT careers_pk PRIMARY KEY (career_id);


--
-- TOC entry 14043 (class 2606 OID 113461)
-- Dependencies: 11771 11771
-- Name: category_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_id_pk PRIMARY KEY (category_id);


--
-- TOC entry 14165 (class 2606 OID 113463)
-- Dependencies: 11869 11869
-- Name: ck_module_class_name; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT ck_module_class_name UNIQUE (module_class_name);


--
-- TOC entry 14051 (class 2606 OID 113465)
-- Dependencies: 11775 11775
-- Name: components_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY components
    ADD CONSTRAINT components_pk PRIMARY KEY (component_id);


--
-- TOC entry 14054 (class 2606 OID 113467)
-- Dependencies: 11777 11777
-- Name: ct_access_level_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_access_level
    ADD CONSTRAINT ct_access_level_pkey PRIMARY KEY (ct_access_level_id);


--
-- TOC entry 14056 (class 2606 OID 113469)
-- Dependencies: 11779 11779
-- Name: ct_address_type_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_address_type
    ADD CONSTRAINT ct_address_type_pkey PRIMARY KEY (ct_address_type_id);


--
-- TOC entry 14059 (class 2606 OID 113471)
-- Dependencies: 11781 11781
-- Name: ct_banner_pos_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_banner_position
    ADD CONSTRAINT ct_banner_pos_pk PRIMARY KEY (ct_banner_position_id);


--
-- TOC entry 14061 (class 2606 OID 113473)
-- Dependencies: 11783 11783
-- Name: ct_banner_sizes_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_banner_sizes
    ADD CONSTRAINT ct_banner_sizes_pk PRIMARY KEY (ct_banner_sizes_id);


--
-- TOC entry 14063 (class 2606 OID 113475)
-- Dependencies: 11785 11785
-- Name: ct_country_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_country
    ADD CONSTRAINT ct_country_pkey PRIMARY KEY (ct_country_id);


--
-- TOC entry 14065 (class 2606 OID 113477)
-- Dependencies: 11787 11787
-- Name: ct_credit_card_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_credit_card
    ADD CONSTRAINT ct_credit_card_pkey PRIMARY KEY (ct_credit_card_id);


--
-- TOC entry 14067 (class 2606 OID 113479)
-- Dependencies: 11789 11789
-- Name: ct_languages_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_languages
    ADD CONSTRAINT ct_languages_pkey PRIMARY KEY (ct_language_id);


--
-- TOC entry 14069 (class 2606 OID 113481)
-- Dependencies: 11791 11791
-- Name: ct_menu_alignment_types_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_menu_alignment_types
    ADD CONSTRAINT ct_menu_alignment_types_pkey PRIMARY KEY (ct_menu_alignment_type_id);


--
-- TOC entry 14071 (class 2606 OID 113483)
-- Dependencies: 11793 11793
-- Name: ct_menu_types_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_menu_types
    ADD CONSTRAINT ct_menu_types_pkey PRIMARY KEY (ct_menu_type_id);


--
-- TOC entry 14073 (class 2606 OID 113485)
-- Dependencies: 11795 11795
-- Name: ct_month_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_month
    ADD CONSTRAINT ct_month_pkey PRIMARY KEY (ct_month_id);


--
-- TOC entry 14075 (class 2606 OID 113487)
-- Dependencies: 11797 11797
-- Name: ct_packages_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_packages
    ADD CONSTRAINT ct_packages_pkey PRIMARY KEY (ct_package_id);


--
-- TOC entry 14077 (class 2606 OID 113489)
-- Dependencies: 11799 11799
-- Name: ct_phone_type_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_phone_type
    ADD CONSTRAINT ct_phone_type_pkey PRIMARY KEY (ct_phone_type_id);


--
-- TOC entry 14079 (class 2606 OID 113491)
-- Dependencies: 11801 11801
-- Name: ct_province_state_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_province_state
    ADD CONSTRAINT ct_province_state_pkey PRIMARY KEY (ct_province_state_id);


--
-- TOC entry 14081 (class 2606 OID 113493)
-- Dependencies: 11803 11803
-- Name: ct_salutation_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_salutation
    ADD CONSTRAINT ct_salutation_pkey PRIMARY KEY (ct_salutation_id);


--
-- TOC entry 14083 (class 2606 OID 113495)
-- Dependencies: 11805 11805
-- Name: ct_term_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ct_term
    ADD CONSTRAINT ct_term_pkey PRIMARY KEY (ct_term_id);


--
-- TOC entry 14085 (class 2606 OID 113497)
-- Dependencies: 11807 11807
-- Name: currency_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency
    ADD CONSTRAINT currency_id_pk PRIMARY KEY (currency_id);


--
-- TOC entry 14095 (class 2606 OID 113499)
-- Dependencies: 11810 11810
-- Name: customer_address_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_address_detail
    ADD CONSTRAINT customer_address_detail_pkey PRIMARY KEY (customer_address_id);


--
-- TOC entry 14098 (class 2606 OID 113501)
-- Dependencies: 11814 11814
-- Name: customer_email_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_email_detail
    ADD CONSTRAINT customer_email_detail_pkey PRIMARY KEY (customer_email_id);


--
-- TOC entry 14101 (class 2606 OID 113503)
-- Dependencies: 11816 11816
-- Name: customer_message_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_messages
    ADD CONSTRAINT customer_message_pk PRIMARY KEY (customer_message_id);


--
-- TOC entry 14103 (class 2606 OID 113505)
-- Dependencies: 11818 11818
-- Name: customer_message_sent_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_messages_sent
    ADD CONSTRAINT customer_message_sent_pk PRIMARY KEY (customer_message_sent_id);


--
-- TOC entry 14106 (class 2606 OID 113507)
-- Dependencies: 11820 11820
-- Name: customer_phone_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_phone_detail
    ADD CONSTRAINT customer_phone_detail_pkey PRIMARY KEY (customer_phone_id);


--
-- TOC entry 14091 (class 2606 OID 113509)
-- Dependencies: 11809 11809
-- Name: customer_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- TOC entry 14109 (class 2606 OID 113511)
-- Dependencies: 11823 11823
-- Name: documents_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (document_id);


--
-- TOC entry 14111 (class 2606 OID 113513)
-- Dependencies: 11824 11824
-- Name: errors_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY errors
    ADD CONSTRAINT errors_pkey PRIMARY KEY (error_id);


--
-- TOC entry 14113 (class 2606 OID 113515)
-- Dependencies: 11826 11826
-- Name: event_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY events
    ADD CONSTRAINT event_pk PRIMARY KEY (event_id);


--
-- TOC entry 14117 (class 2606 OID 113517)
-- Dependencies: 11829 11829
-- Name: faq_categories_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY faq_categories
    ADD CONSTRAINT faq_categories_pk PRIMARY KEY (faq_categories_id);


--
-- TOC entry 14115 (class 2606 OID 113519)
-- Dependencies: 11828 11828
-- Name: faq_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY faq
    ADD CONSTRAINT faq_pk PRIMARY KEY (faq_id);


--
-- TOC entry 14120 (class 2606 OID 113521)
-- Dependencies: 11832 11832
-- Name: images_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY images
    ADD CONSTRAINT images_pkey PRIMARY KEY (image_id);


--
-- TOC entry 14124 (class 2606 OID 113523)
-- Dependencies: 11835 11835
-- Name: item_attributes_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item_attributes
    ADD CONSTRAINT item_attributes_id_pk PRIMARY KEY (item_attributes_id);


--
-- TOC entry 14122 (class 2606 OID 113525)
-- Dependencies: 11834 11834
-- Name: item_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_id_pk PRIMARY KEY (item_id);


--
-- TOC entry 14132 (class 2606 OID 113527)
-- Dependencies: 11839 11839
-- Name: item_images_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item_images
    ADD CONSTRAINT item_images_pkey PRIMARY KEY (item_image_id);


--
-- TOC entry 14134 (class 2606 OID 113529)
-- Dependencies: 11842 11842
-- Name: item_shipping_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item_shipping
    ADD CONSTRAINT item_shipping_id_pk PRIMARY KEY (item_shipping_id);


--
-- TOC entry 14129 (class 2606 OID 113531)
-- Dependencies: 11837 11837
-- Name: itemcategory_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item_category
    ADD CONSTRAINT itemcategory_id_pk PRIMARY KEY (item_category_id);


--
-- TOC entry 14141 (class 2606 OID 113533)
-- Dependencies: 11848 11848
-- Name: jsp_template_name; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY jsp_templates
    ADD CONSTRAINT jsp_template_name UNIQUE (jsp_template_name);


--
-- TOC entry 14139 (class 2606 OID 113535)
-- Dependencies: 11846 11846
-- Name: jsp_template_page_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY jsp_template_page
    ADD CONSTRAINT jsp_template_page_pk PRIMARY KEY (jsp_template_page_id);


--
-- TOC entry 14143 (class 2606 OID 113537)
-- Dependencies: 11848 11848
-- Name: jsp_template_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY jsp_templates
    ADD CONSTRAINT jsp_template_pk PRIMARY KEY (jsp_template_id);


--
-- TOC entry 14145 (class 2606 OID 113539)
-- Dependencies: 11850 11850
-- Name: link_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY links
    ADD CONSTRAINT link_pk PRIMARY KEY (link_id);


--
-- TOC entry 14147 (class 2606 OID 113541)
-- Dependencies: 11851 11851
-- Name: links_cat_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY links_categories
    ADD CONSTRAINT links_cat_pk PRIMARY KEY (links_cat_id);


--
-- TOC entry 14149 (class 2606 OID 113543)
-- Dependencies: 11855 11855
-- Name: liupk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY logged_in_users
    ADD CONSTRAINT liupk PRIMARY KEY (logged_in_user_id);


--
-- TOC entry 14151 (class 2606 OID 113545)
-- Dependencies: 11857 11857
-- Name: mailing_lists_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mailing_lists
    ADD CONSTRAINT mailing_lists_pkey PRIMARY KEY (list_id);


--
-- TOC entry 14158 (class 2606 OID 113547)
-- Dependencies: 11861 11861
-- Name: menu_item_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY menu_item_detail
    ADD CONSTRAINT menu_item_detail_pkey PRIMARY KEY (menu_item_detail_id);


--
-- TOC entry 14156 (class 2606 OID 113549)
-- Dependencies: 11860 11860
-- Name: menu_item_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY menu_item
    ADD CONSTRAINT menu_item_pkey PRIMARY KEY (menu_item_id);


--
-- TOC entry 14153 (class 2606 OID 113551)
-- Dependencies: 11859 11859
-- Name: menu_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (menu_id);


--
-- TOC entry 14161 (class 2606 OID 113553)
-- Dependencies: 11865 11865
-- Name: message_queue_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY message_queue
    ADD CONSTRAINT message_queue_pk PRIMARY KEY (message_queue_id);


--
-- TOC entry 14163 (class 2606 OID 113555)
-- Dependencies: 11867 11867
-- Name: module_position_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY module_position
    ADD CONSTRAINT module_position_pkey PRIMARY KEY (module_position_id);


--
-- TOC entry 14167 (class 2606 OID 113557)
-- Dependencies: 11869 11869
-- Name: modules_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT modules_pkey PRIMARY KEY (module_id);


--
-- TOC entry 14171 (class 2606 OID 113559)
-- Dependencies: 11872 11872
-- Name: news_categories_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY news_categories
    ADD CONSTRAINT news_categories_pkey PRIMARY KEY (news_categories_id);


--
-- TOC entry 14169 (class 2606 OID 113561)
-- Dependencies: 11871 11871
-- Name: news_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (news_id);


--
-- TOC entry 14173 (class 2606 OID 113563)
-- Dependencies: 11875 11875
-- Name: news_ticker_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY news_ticker
    ADD CONSTRAINT news_ticker_pk PRIMARY KEY (news_ticker_id);


--
-- TOC entry 14177 (class 2606 OID 113565)
-- Dependencies: 11878 11878
-- Name: order_details_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders_detail
    ADD CONSTRAINT order_details_id_pk PRIMARY KEY (orders_detail_id);


--
-- TOC entry 14175 (class 2606 OID 113567)
-- Dependencies: 11877 11877
-- Name: order_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT order_id_pk PRIMARY KEY (order_id);


--
-- TOC entry 14184 (class 2606 OID 113569)
-- Dependencies: 11882 11882
-- Name: page_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page_detail
    ADD CONSTRAINT page_detail_pkey PRIMARY KEY (page_detail_id);


--
-- TOC entry 14189 (class 2606 OID 113571)
-- Dependencies: 11885 11885
-- Name: page_group_detail_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page_group_detail
    ADD CONSTRAINT page_group_detail_pk PRIMARY KEY (page_group_detail_id);


--
-- TOC entry 14187 (class 2606 OID 113573)
-- Dependencies: 11884 11884
-- Name: page_group_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page_group
    ADD CONSTRAINT page_group_id_pk PRIMARY KEY (page_group_id);


--
-- TOC entry 14193 (class 2606 OID 113575)
-- Dependencies: 11888 11888
-- Name: page_history_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page_history
    ADD CONSTRAINT page_history_pk PRIMARY KEY (page_history_id);


--
-- TOC entry 14180 (class 2606 OID 113577)
-- Dependencies: 11881 11881
-- Name: page_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page
    ADD CONSTRAINT page_pkey PRIMARY KEY (page_id);


--
-- TOC entry 14196 (class 2606 OID 113579)
-- Dependencies: 11891 11891
-- Name: page_role_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page_role
    ADD CONSTRAINT page_role_id_pk PRIMARY KEY (page_role_id);


--
-- TOC entry 14047 (class 2606 OID 113581)
-- Dependencies: 11773 11773
-- Name: pk_class_objects; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY class_objects
    ADD CONSTRAINT pk_class_objects PRIMARY KEY (class_object_id);


--
-- TOC entry 14198 (class 2606 OID 113583)
-- Dependencies: 11893 11893
-- Name: pmc_story_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pmc_stories
    ADD CONSTRAINT pmc_story_pk PRIMARY KEY (story_id);


--
-- TOC entry 14208 (class 2606 OID 113585)
-- Dependencies: 11902 11902
-- Name: poll_option_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY polls_data
    ADD CONSTRAINT poll_option_pk PRIMARY KEY (polls_data_id);


--
-- TOC entry 14206 (class 2606 OID 113587)
-- Dependencies: 11900 11900
-- Name: polls_check_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY polls_check
    ADD CONSTRAINT polls_check_pk PRIMARY KEY (polls_check_id);


--
-- TOC entry 14204 (class 2606 OID 113589)
-- Dependencies: 11899 11899
-- Name: polls_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY polls
    ADD CONSTRAINT polls_pk PRIMARY KEY (poll_id);


--
-- TOC entry 14210 (class 2606 OID 113591)
-- Dependencies: 11905 11905
-- Name: promotions_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY promotions
    ADD CONSTRAINT promotions_pk PRIMARY KEY (promotion_id);


--
-- TOC entry 14212 (class 2606 OID 113593)
-- Dependencies: 11907 11907
-- Name: quotes_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY quotes
    ADD CONSTRAINT quotes_pkey PRIMARY KEY (quote_id);


--
-- TOC entry 14214 (class 2606 OID 113595)
-- Dependencies: 11909 11909
-- Name: role_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "role"
    ADD CONSTRAINT role_id_pk PRIMARY KEY (role_id);


--
-- TOC entry 14218 (class 2606 OID 113597)
-- Dependencies: 11914 11914
-- Name: sessions_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sessions
    ADD CONSTRAINT sessions_pk PRIMARY KEY (session_id);


--
-- TOC entry 14220 (class 2606 OID 113599)
-- Dependencies: 11916 11916
-- Name: shipping_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY shipping
    ADD CONSTRAINT shipping_id_pk PRIMARY KEY (shipping_id);


--
-- TOC entry 14222 (class 2606 OID 113601)
-- Dependencies: 11917 11917
-- Name: shipping_rate_method_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY shipping_rate_method
    ADD CONSTRAINT shipping_rate_method_pk PRIMARY KEY (shipping_rate_method_id);


--
-- TOC entry 14136 (class 2606 OID 113603)
-- Dependencies: 11844 11844
-- Name: special_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY item_special
    ADD CONSTRAINT special_id_pk PRIMARY KEY (item_special_id);


--
-- TOC entry 14202 (class 2606 OID 113605)
-- Dependencies: 11897 11897
-- Name: story_category_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pmc_story_category
    ADD CONSTRAINT story_category_pk PRIMARY KEY (story_category_id);


--
-- TOC entry 14200 (class 2606 OID 113607)
-- Dependencies: 11894 11894
-- Name: story_comment_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pmc_stories_comments
    ADD CONSTRAINT story_comment_pk PRIMARY KEY (comment_id);


--
-- TOC entry 14224 (class 2606 OID 113609)
-- Dependencies: 11922 11922
-- Name: style_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY style
    ADD CONSTRAINT style_pkey PRIMARY KEY (style_id);


--
-- TOC entry 14228 (class 2606 OID 113611)
-- Dependencies: 11925 11925
-- Name: system_messages_detail_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY system_messages_detail
    ADD CONSTRAINT system_messages_detail_pkey PRIMARY KEY (system_messages_detail_id);


--
-- TOC entry 14226 (class 2606 OID 113613)
-- Dependencies: 11924 11924
-- Name: system_messages_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY system_messages
    ADD CONSTRAINT system_messages_pkey PRIMARY KEY (system_message_id);


--
-- TOC entry 14232 (class 2606 OID 113615)
-- Dependencies: 11928 11928
-- Name: system_pages_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY system_pages
    ADD CONSTRAINT system_pages_pkey PRIMARY KEY (system_page_id);


--
-- TOC entry 14234 (class 2606 OID 113617)
-- Dependencies: 11931 11931
-- Name: tax_id_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tax
    ADD CONSTRAINT tax_id_pk PRIMARY KEY (tax_id);


--
-- TOC entry 14236 (class 2606 OID 113619)
-- Dependencies: 11933 11933
-- Name: template_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "template"
    ADD CONSTRAINT template_pkey PRIMARY KEY (template_id);


--
-- TOC entry 14238 (class 2606 OID 113621)
-- Dependencies: 11941 11941
-- Name: turba_objects_pkey; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY turba_objects
    ADD CONSTRAINT turba_objects_pkey PRIMARY KEY (object_id);


--
-- TOC entry 14049 (class 2606 OID 113623)
-- Dependencies: 11773 11773
-- Name: uniq_class_object; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY class_objects
    ADD CONSTRAINT uniq_class_object UNIQUE (class_object);


--
-- TOC entry 14182 (class 2606 OID 113625)
-- Dependencies: 11881 11881
-- Name: unq_page_name; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY page
    ADD CONSTRAINT unq_page_name UNIQUE (page_name);


--
-- TOC entry 14216 (class 2606 OID 113627)
-- Dependencies: 11909 11909
-- Name: unq_role_name; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "role"
    ADD CONSTRAINT unq_role_name UNIQUE (role_name);


--
-- TOC entry 14230 (class 2606 OID 113629)
-- Dependencies: 11925 11925
-- Name: unq_system_message_detail_display_name; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY system_messages_detail
    ADD CONSTRAINT unq_system_message_detail_display_name UNIQUE (system_messages_detail_display_name);


--
-- TOC entry 14241 (class 2606 OID 113631)
-- Dependencies: 11942 11942
-- Name: user_role_pk; Type: CONSTRAINT; Schema: verilion; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pk PRIMARY KEY (user_role_id);


--
-- TOC entry 14008 (class 1259 OID 114810)
-- Dependencies: 11735
-- Name: admin_menu_item_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX admin_menu_item_ndx ON admin_menu_item_detail USING btree (admin_menu_items_id);


--
-- TOC entry 14010 (class 1259 OID 114811)
-- Dependencies: 11736
-- Name: admin_menu_items_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX admin_menu_items_id_ndx ON admin_menu_items USING btree (admin_menu_items_id);


--
-- TOC entry 14009 (class 1259 OID 114812)
-- Dependencies: 11735 11735
-- Name: admin_menu_items_sort_order_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX admin_menu_items_sort_order_ndx ON admin_menu_item_detail USING btree (admin_menu_items_id, admin_menu_item_detail_order);


--
-- TOC entry 14052 (class 1259 OID 114813)
-- Dependencies: 11777
-- Name: ct_access_leve_id_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX ct_access_leve_id_index ON ct_access_level USING btree (ct_access_level_id);


--
-- TOC entry 14092 (class 1259 OID 114814)
-- Dependencies: 11810
-- Name: customer_address_detail_customer_id_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_address_detail_customer_id_index ON customer_address_detail USING btree (customer_id);


--
-- TOC entry 14093 (class 1259 OID 114815)
-- Dependencies: 11810
-- Name: customer_address_detail_id_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_address_detail_id_index ON customer_address_detail USING btree (customer_address_id);


--
-- TOC entry 14086 (class 1259 OID 114816)
-- Dependencies: 11809
-- Name: customer_customer_access_level_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_customer_access_level_index ON customer USING btree (customer_access_level);


--
-- TOC entry 14087 (class 1259 OID 114817)
-- Dependencies: 11809
-- Name: customer_customer_first_name_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_customer_first_name_index ON customer USING btree (customer_first_name);


--
-- TOC entry 14088 (class 1259 OID 114818)
-- Dependencies: 11809
-- Name: customer_customer_id_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_customer_id_index ON customer USING btree (customer_id);


--
-- TOC entry 14089 (class 1259 OID 114819)
-- Dependencies: 11809
-- Name: customer_customer_last_name_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_customer_last_name_index ON customer USING btree (customer_last_name);


--
-- TOC entry 14096 (class 1259 OID 114820)
-- Dependencies: 11814
-- Name: customer_email_customer_email_detail_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_email_customer_email_detail_index ON customer_email_detail USING btree (customer_email);


--
-- TOC entry 14099 (class 1259 OID 114821)
-- Dependencies: 11814
-- Name: customer_id_customer_email_detail_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_id_customer_email_detail_index ON customer_email_detail USING btree (customer_id);


--
-- TOC entry 14104 (class 1259 OID 114822)
-- Dependencies: 11820
-- Name: customer_id_customer_phone_detail_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_id_customer_phone_detail_index ON customer_phone_detail USING btree (customer_id);


--
-- TOC entry 14118 (class 1259 OID 114823)
-- Dependencies: 11832
-- Name: customer_id_images_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX customer_id_images_index ON images USING btree (customer_id);


--
-- TOC entry 14107 (class 1259 OID 114824)
-- Dependencies: 11823
-- Name: documents_page_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX documents_page_id_ndx ON documents USING btree (page_id);


--
-- TOC entry 14190 (class 1259 OID 114825)
-- Dependencies: 11888
-- Name: dt_ndx_ph; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX dt_ndx_ph ON page_history USING btree (date_time);


--
-- TOC entry 14125 (class 1259 OID 114826)
-- Dependencies: 11835
-- Name: item_attributes_item_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX item_attributes_item_id_ndx ON item_attributes USING btree (item_id);


--
-- TOC entry 14126 (class 1259 OID 114827)
-- Dependencies: 11837
-- Name: item_category_category_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX item_category_category_id_ndx ON item_category USING btree (category_id);


--
-- TOC entry 14127 (class 1259 OID 114828)
-- Dependencies: 11837
-- Name: item_category_item_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX item_category_item_id_ndx ON item_category USING btree (item_id);


--
-- TOC entry 14130 (class 1259 OID 114829)
-- Dependencies: 11839
-- Name: item_images_item_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX item_images_item_id_ndx ON item_images USING btree (item_id);


--
-- TOC entry 14154 (class 1259 OID 114830)
-- Dependencies: 11860
-- Name: menu_id_menu_item_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX menu_id_menu_item_index ON menu_item USING btree (menu_id);


--
-- TOC entry 14159 (class 1259 OID 114831)
-- Dependencies: 11861
-- Name: menu_item_menu_item_detail_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX menu_item_menu_item_detail_index ON menu_item_detail USING btree (menu_item_id);


--
-- TOC entry 14044 (class 1259 OID 114832)
-- Dependencies: 11773
-- Name: ndx_class_object; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX ndx_class_object ON class_objects USING btree (class_object);


--
-- TOC entry 14045 (class 1259 OID 114833)
-- Dependencies: 11773
-- Name: ndx_class_objects; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX ndx_class_objects ON class_objects USING btree (class_object_id);


--
-- TOC entry 14057 (class 1259 OID 114834)
-- Dependencies: 11779
-- Name: ndx_ct_address_type; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX ndx_ct_address_type ON ct_address_type USING btree (ct_address_type_id);


--
-- TOC entry 14191 (class 1259 OID 114835)
-- Dependencies: 11888 11888
-- Name: page_dt_ndx_ph; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX page_dt_ndx_ph ON page_history USING btree (page_id, date_time);


--
-- TOC entry 14185 (class 1259 OID 114836)
-- Dependencies: 11882
-- Name: page_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX page_id_ndx ON page_detail USING btree (page_id);


--
-- TOC entry 14178 (class 1259 OID 114837)
-- Dependencies: 11881
-- Name: page_name_page_index; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX page_name_page_index ON page USING btree (page_name);


--
-- TOC entry 14194 (class 1259 OID 114838)
-- Dependencies: 11888
-- Name: page_ndx_ph; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX page_ndx_ph ON page_history USING btree (page_id);


--
-- TOC entry 14013 (class 1259 OID 114839)
-- Dependencies: 11736
-- Name: sort_order_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX sort_order_ndx ON admin_menu_items USING btree (admin_menu_items_order);


--
-- TOC entry 14137 (class 1259 OID 114840)
-- Dependencies: 11844
-- Name: special_item_item_id_ndx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX special_item_item_id_ndx ON item_special USING btree (item_id);


--
-- TOC entry 14239 (class 1259 OID 114841)
-- Dependencies: 11941
-- Name: turba_owner_idx; Type: INDEX; Schema: verilion; Owner: -; Tablespace: 
--

CREATE INDEX turba_owner_idx ON turba_objects USING btree (owner_id);


--
-- TOC entry 14246 (class 2606 OID 121806)
-- Dependencies: 14014 11740 11741
-- Name: $1; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_detail
    ADD CONSTRAINT "$1" FOREIGN KEY (page_id) REFERENCES admin_page(page_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14243 (class 2606 OID 121811)
-- Dependencies: 14004 11734 11736
-- Name: $2; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_menu_items
    ADD CONSTRAINT "$2" FOREIGN KEY (admin_menu_id) REFERENCES admin_menu(admin_menu_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14245 (class 2606 OID 121816)
-- Dependencies: 14066 11789 11741
-- Name: $2; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_detail
    ADD CONSTRAINT "$2" FOREIGN KEY (ct_language_id) REFERENCES ct_languages(ct_language_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14248 (class 2606 OID 121821)
-- Dependencies: 14018 11743 11745
-- Name: admin_page_group_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_group_detail
    ADD CONSTRAINT admin_page_group_id FOREIGN KEY (admin_page_group_id) REFERENCES admin_page_group(admin_page_group_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14252 (class 2606 OID 121826)
-- Dependencies: 14018 11743 11750
-- Name: admin_page_group_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_role
    ADD CONSTRAINT admin_page_group_id_fk FOREIGN KEY (admin_page_id) REFERENCES admin_page_group(admin_page_group_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14247 (class 2606 OID 121831)
-- Dependencies: 14014 11740 11745
-- Name: admin_page_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_group_detail
    ADD CONSTRAINT admin_page_id_fk FOREIGN KEY (admin_page_id) REFERENCES admin_page(page_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14250 (class 2606 OID 121836)
-- Dependencies: 14014 11740 11747
-- Name: admin_page_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_group_role
    ADD CONSTRAINT admin_page_id_fk FOREIGN KEY (admin_page_id) REFERENCES admin_page(page_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14257 (class 2606 OID 121841)
-- Dependencies: 14058 11781 11761
-- Name: banner_pos_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY banners
    ADD CONSTRAINT banner_pos_fk FOREIGN KEY (ct_banner_position_id) REFERENCES ct_banner_position(ct_banner_position_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14256 (class 2606 OID 121846)
-- Dependencies: 14060 11783 11761
-- Name: banner_size_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY banners
    ADD CONSTRAINT banner_size_fk FOREIGN KEY (ct_banner_sizes_id) REFERENCES ct_banner_sizes(ct_banner_sizes_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14277 (class 2606 OID 121851)
-- Dependencies: 14042 11771 11837
-- Name: category_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_category
    ADD CONSTRAINT category_id FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14312 (class 2606 OID 121856)
-- Dependencies: 14062 11785 11931
-- Name: ct_country_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY tax
    ADD CONSTRAINT ct_country_id FOREIGN KEY (ct_country_id) REFERENCES ct_country(ct_country_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14296 (class 2606 OID 121861)
-- Dependencies: 14062 11785 11877
-- Name: ct_country_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT ct_country_id_fk FOREIGN KEY (ct_country_id) REFERENCES ct_country(ct_country_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14295 (class 2606 OID 121866)
-- Dependencies: 14078 11801 11877
-- Name: ct_province_state_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT ct_province_state_fk FOREIGN KEY (ct_province_state_id) REFERENCES ct_province_state(ct_province_state_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14311 (class 2606 OID 121871)
-- Dependencies: 14078 11801 11931
-- Name: ct_province_state_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY tax
    ADD CONSTRAINT ct_province_state_id_fk FOREIGN KEY (ct_province_state_id) REFERENCES ct_province_state(ct_province_state_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14314 (class 2606 OID 121876)
-- Dependencies: 14090 11809 11942
-- Name: customer_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14294 (class 2606 OID 121881)
-- Dependencies: 14090 11809 11877
-- Name: customer_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14274 (class 2606 OID 121886)
-- Dependencies: 14116 11829 11828
-- Name: faq_categories_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY faq
    ADD CONSTRAINT faq_categories_id_fk FOREIGN KEY (faq_categories_id) REFERENCES faq_categories(faq_categories_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14254 (class 2606 OID 121891)
-- Dependencies: 14053 11777 11753
-- Name: fk_access_level_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY archive_page
    ADD CONSTRAINT fk_access_level_id FOREIGN KEY (ct_access_level_id) REFERENCES ct_access_level(ct_access_level_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14242 (class 2606 OID 121896)
-- Dependencies: 14011 11736 11735
-- Name: fk_admin_menu_items_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_menu_item_detail
    ADD CONSTRAINT fk_admin_menu_items_id FOREIGN KEY (admin_menu_items_id) REFERENCES admin_menu_items(admin_menu_items_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14255 (class 2606 OID 121901)
-- Dependencies: 14026 11753 11754
-- Name: fk_archive_page_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY archive_page_entry
    ADD CONSTRAINT fk_archive_page_id FOREIGN KEY (archive_page_id) REFERENCES archive_page(archive_page_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14263 (class 2606 OID 121906)
-- Dependencies: 14053 11777 11809
-- Name: fk_ct_access_level_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_ct_access_level_id FOREIGN KEY (customer_access_level) REFERENCES ct_access_level(ct_access_level_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14310 (class 2606 OID 121911)
-- Dependencies: 14053 11777 11928
-- Name: fk_ct_access_level_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY system_pages
    ADD CONSTRAINT fk_ct_access_level_id FOREIGN KEY (ct_access_level_id) REFERENCES ct_access_level(ct_access_level_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14267 (class 2606 OID 121916)
-- Dependencies: 14055 11779 11810
-- Name: fk_ct_address_type_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_address_detail
    ADD CONSTRAINT fk_ct_address_type_id FOREIGN KEY (ct_address_type_id) REFERENCES ct_address_type(ct_address_type_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14266 (class 2606 OID 121921)
-- Dependencies: 14062 11785 11810
-- Name: fk_ct_country_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_address_detail
    ADD CONSTRAINT fk_ct_country_id FOREIGN KEY (ct_country_id) REFERENCES ct_country(ct_country_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14262 (class 2606 OID 121926)
-- Dependencies: 14064 11787 11809
-- Name: fk_ct_credit_card_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_ct_credit_card_id FOREIGN KEY (ct_credit_card_id) REFERENCES ct_credit_card(ct_credit_card_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14290 (class 2606 OID 121931)
-- Dependencies: 14066 11789 11861
-- Name: fk_ct_language_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu_item_detail
    ADD CONSTRAINT fk_ct_language_id FOREIGN KEY (ct_language_id) REFERENCES ct_languages(ct_language_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14300 (class 2606 OID 121936)
-- Dependencies: 14066 11789 11882
-- Name: fk_ct_language_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_detail
    ADD CONSTRAINT fk_ct_language_id FOREIGN KEY (ct_language_id) REFERENCES ct_languages(ct_language_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14309 (class 2606 OID 121941)
-- Dependencies: 14066 11789 11925
-- Name: fk_ct_language_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY system_messages_detail
    ADD CONSTRAINT fk_ct_language_id FOREIGN KEY (ct_language_id) REFERENCES ct_languages(ct_language_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14286 (class 2606 OID 121946)
-- Dependencies: 14068 11791 11859
-- Name: fk_ct_menu_alignment_type_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk_ct_menu_alignment_type_id FOREIGN KEY (ct_menu_alignment_type_id) REFERENCES ct_menu_alignment_types(ct_menu_alignment_type_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14285 (class 2606 OID 121951)
-- Dependencies: 14070 11793 11859
-- Name: fk_ct_menu_type_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk_ct_menu_type_id FOREIGN KEY (ct_menu_type_id) REFERENCES ct_menu_types(ct_menu_type_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14261 (class 2606 OID 121956)
-- Dependencies: 14074 11797 11809
-- Name: fk_ct_package_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_ct_package_id FOREIGN KEY (ct_package_id) REFERENCES ct_packages(ct_package_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14272 (class 2606 OID 121961)
-- Dependencies: 14076 11799 11820
-- Name: fk_ct_phone_type_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_phone_detail
    ADD CONSTRAINT fk_ct_phone_type_id FOREIGN KEY (ct_phone_type_id) REFERENCES ct_phone_type(ct_phone_type_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14265 (class 2606 OID 121966)
-- Dependencies: 14078 11801 11810
-- Name: fk_ct_province_state_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_address_detail
    ADD CONSTRAINT fk_ct_province_state_id FOREIGN KEY (ct_province_state_id) REFERENCES ct_province_state(ct_province_state_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14260 (class 2606 OID 121971)
-- Dependencies: 14080 11803 11809
-- Name: fk_ct_salutation_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_ct_salutation_id FOREIGN KEY (ct_salutation_id) REFERENCES ct_salutation(ct_salutation_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14259 (class 2606 OID 121976)
-- Dependencies: 14082 11805 11809
-- Name: fk_ct_term_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_ct_term_id FOREIGN KEY (ct_term_id) REFERENCES ct_term(ct_term_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14268 (class 2606 OID 121981)
-- Dependencies: 14090 11809 11814
-- Name: fk_customer_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_email_detail
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14271 (class 2606 OID 121986)
-- Dependencies: 14090 11809 11820
-- Name: fk_customer_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_phone_detail
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14264 (class 2606 OID 121991)
-- Dependencies: 14090 11809 11810
-- Name: fk_customer_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_address_detail
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14284 (class 2606 OID 121996)
-- Dependencies: 14090 11809 11857
-- Name: fk_customer_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY mailing_lists
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14270 (class 2606 OID 122001)
-- Dependencies: 14090 11809 11816
-- Name: fk_from_user_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_messages
    ADD CONSTRAINT fk_from_user_id_fk FOREIGN KEY (from_user_id) REFERENCES customer(customer_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14288 (class 2606 OID 122006)
-- Dependencies: 14152 11859 11860
-- Name: fk_menu_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu_item
    ADD CONSTRAINT fk_menu_id FOREIGN KEY (menu_id) REFERENCES menu(menu_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14289 (class 2606 OID 122011)
-- Dependencies: 14155 11860 11861
-- Name: fk_menu_item_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu_item_detail
    ADD CONSTRAINT fk_menu_item_id FOREIGN KEY (menu_item_id) REFERENCES menu_item(menu_item_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14291 (class 2606 OID 122016)
-- Dependencies: 14162 11867 11869
-- Name: fk_module_position_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT fk_module_position_id FOREIGN KEY (module_position_id) REFERENCES module_position(module_position_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14292 (class 2606 OID 122021)
-- Dependencies: 14170 11872 11871
-- Name: fk_news_categories_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY news
    ADD CONSTRAINT fk_news_categories_id FOREIGN KEY (news_categories_id) REFERENCES news_categories(news_categories_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14273 (class 2606 OID 122026)
-- Dependencies: 14179 11881 11823
-- Name: fk_page_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT fk_page_id FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14299 (class 2606 OID 122031)
-- Dependencies: 14179 11881 11882
-- Name: fk_page_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_detail
    ADD CONSTRAINT fk_page_id FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14308 (class 2606 OID 122036)
-- Dependencies: 14225 11924 11925
-- Name: fk_system_message_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY system_messages_detail
    ADD CONSTRAINT fk_system_message_id FOREIGN KEY (system_message_id) REFERENCES system_messages(system_message_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14244 (class 2606 OID 122041)
-- Dependencies: 14235 11933 11740
-- Name: fk_template_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page
    ADD CONSTRAINT fk_template_id FOREIGN KEY (template_id) REFERENCES "template"(template_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 14298 (class 2606 OID 122046)
-- Dependencies: 14235 11933 11881
-- Name: fk_template_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page
    ADD CONSTRAINT fk_template_id FOREIGN KEY (template_id) REFERENCES "template"(template_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 14253 (class 2606 OID 122051)
-- Dependencies: 14235 11933 11753
-- Name: fk_template_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY archive_page
    ADD CONSTRAINT fk_template_id FOREIGN KEY (template_id) REFERENCES "template"(template_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 14269 (class 2606 OID 122056)
-- Dependencies: 14090 11809 11816
-- Name: fk_to_customer_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY customer_messages
    ADD CONSTRAINT fk_to_customer_id FOREIGN KEY (to_user_id) REFERENCES customer(customer_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14281 (class 2606 OID 122061)
-- Dependencies: 14121 11834 11844
-- Name: item_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_special
    ADD CONSTRAINT item_id FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 14278 (class 2606 OID 122066)
-- Dependencies: 14121 11834 11839
-- Name: item_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_images
    ADD CONSTRAINT item_id_fk FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14276 (class 2606 OID 122071)
-- Dependencies: 14121 11834 11837
-- Name: item_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_category
    ADD CONSTRAINT item_id_fk FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14275 (class 2606 OID 122076)
-- Dependencies: 14121 11834 11835
-- Name: item_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_attributes
    ADD CONSTRAINT item_id_fk FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14280 (class 2606 OID 122081)
-- Dependencies: 14121 11834 11842
-- Name: item_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_shipping
    ADD CONSTRAINT item_id_fk FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14283 (class 2606 OID 122086)
-- Dependencies: 14142 11848 11846
-- Name: jsp_template_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY jsp_template_page
    ADD CONSTRAINT jsp_template_id FOREIGN KEY (jsp_template_id) REFERENCES jsp_templates(jsp_template_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14287 (class 2606 OID 122091)
-- Dependencies: 14179 11881 11860
-- Name: menu_item_page_id_fkey; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY menu_item
    ADD CONSTRAINT menu_item_page_id_fkey FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 14293 (class 2606 OID 122096)
-- Dependencies: 14168 11871 11875
-- Name: news_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY news_ticker
    ADD CONSTRAINT news_id_fk FOREIGN KEY (news_id) REFERENCES news(news_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14297 (class 2606 OID 122101)
-- Dependencies: 14174 11877 11878
-- Name: order_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY orders_detail
    ADD CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES orders(order_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14302 (class 2606 OID 122106)
-- Dependencies: 14186 11884 11885
-- Name: page_group_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_group_detail
    ADD CONSTRAINT page_group_id_fk FOREIGN KEY (page_group_id) REFERENCES page_group(page_group_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14305 (class 2606 OID 122111)
-- Dependencies: 14186 11884 11891
-- Name: page_group_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_role
    ADD CONSTRAINT page_group_id_fk FOREIGN KEY (page_id) REFERENCES page_group(page_group_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14303 (class 2606 OID 122116)
-- Dependencies: 14179 11881 11888
-- Name: page_history_page_id_fkey; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_history
    ADD CONSTRAINT page_history_page_id_fkey FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14301 (class 2606 OID 122121)
-- Dependencies: 14179 11881 11885
-- Name: page_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_group_detail
    ADD CONSTRAINT page_id FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14282 (class 2606 OID 122126)
-- Dependencies: 14179 11881 11846
-- Name: page_id; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY jsp_template_page
    ADD CONSTRAINT page_id FOREIGN KEY (page_id) REFERENCES page(page_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14258 (class 2606 OID 122131)
-- Dependencies: 14042 11771 11771
-- Name: parent_category_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY category
    ADD CONSTRAINT parent_category_id_fk FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14306 (class 2606 OID 122136)
-- Dependencies: 14203 11899 11900
-- Name: poll_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY polls_check
    ADD CONSTRAINT poll_id_fk FOREIGN KEY (poll_id) REFERENCES polls(poll_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14307 (class 2606 OID 122141)
-- Dependencies: 14203 11899 11902
-- Name: poll_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY polls_data
    ADD CONSTRAINT poll_id_fk FOREIGN KEY (poll_id) REFERENCES polls(poll_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 14304 (class 2606 OID 122146)
-- Dependencies: 14213 11909 11891
-- Name: role_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY page_role
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES "role"(role_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14251 (class 2606 OID 122151)
-- Dependencies: 14213 11909 11750
-- Name: role_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_role
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES "role"(role_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14313 (class 2606 OID 122156)
-- Dependencies: 14213 11909 11942
-- Name: role_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES "role"(role_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14249 (class 2606 OID 122161)
-- Dependencies: 14213 11909 11747
-- Name: role_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY admin_page_group_role
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES "role"(role_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14279 (class 2606 OID 122166)
-- Dependencies: 14219 11916 11842
-- Name: shipping_id_fk; Type: FK CONSTRAINT; Schema: verilion; Owner: -
--

ALTER TABLE ONLY item_shipping
    ADD CONSTRAINT shipping_id_fk FOREIGN KEY (shipping_id) REFERENCES shipping(shipping_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 14368 (class 0 OID 0)
-- Dependencies: 11941
-- Name: turba_objects; Type: ACL; Schema: verilion; Owner: -
--

REVOKE ALL ON TABLE turba_objects FROM PUBLIC;
REVOKE ALL ON TABLE turba_objects FROM postgres;
GRANT ALL ON TABLE turba_objects TO postgres;


-- Completed on 2007-06-11 14:11:48 EDT

--
-- PostgreSQL database dump complete
--

