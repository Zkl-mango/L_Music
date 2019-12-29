CREATE TABLE `User` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`password`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`avatar`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`follow`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关注的歌手id' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `singer` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`singer`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`english_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`nationality`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`sex`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`picture`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`fans`  int(11) NULL DEFAULT NULL ,
`songs`  int(11) NULL DEFAULT NULL ,
`albums`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `album` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`singer_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`time`  datetime NOT NULL ,
`hot`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_singer` (`singer_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `song` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`singer_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`album_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`picture`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`lyric`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL ,
`duration`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`category`  int(255) NOT NULL ,
`hot`  int(255) NOT NULL ,
`recommend`  int(255) NOT NULL ,
`increase`  int(255) NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `song_list` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`list_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`category`  int(11) NOT NULL COMMENT '1：自定义；2：我喜欢的' ,
`user_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_list_user` (`user_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `song_details` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`song_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`song_list`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`create_at`  datetime NULL DEFAULT NULL ,
`deleted`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`song_list`) REFERENCES `song_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_detail_song` (`song_id`) USING BTREE ,
INDEX `fk_detail_list` (`song_list`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `history_list` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`song_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`user_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_history_song` (`song_id`) USING BTREE ,
INDEX `fk_history_user` (`user_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `play_list` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`user_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`song_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`is_play`  int(2) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_play_user` (`user_id`) USING BTREE ,
INDEX `fk_play_song` (`song_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `comments` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`comment`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`user_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`song_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`likes`  int(11) NULL DEFAULT NULL ,
`time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`song_id`) REFERENCES `song` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `fk_comment_user` (`user_id`) USING BTREE ,
INDEX `fk_comment_song` (`song_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=DYNAMIC
;




