/*
 * 数据库建表语句
 * @author haolen
 * @version 1.0
 * @time 2018/10/23
 */


/*
 * 下面是关于钱包的表
 */
CREATE TABLE `wallet` (
  `id` int(11) AUTO_INCREMENT,
  `wallet_name` varchar(255) DEFAULT NULL,
  `wallet_password` varchar(255) DEFAULT NULL,
  `wallet_address` varchar(255) DEFAULT NULL,
  `wallet_mnemonic` varchar(255) DEFAULT NULL,
  `wallet_credits` float  DEFAULT NULL,
  `public_key` varchar(255) DEFAULT NULL,
  `private_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

CREATE TABLE `wallet_deal_detail` (
  `id` int(11) AUTO_INCREMENT,
  `deal_time` varchar(255) DEFAULT NULL COMMENT '交易时间',
  `wallet_out_address` varchar(255) DEFAULT NULL COMMENT '钱包转出地址',
  `wallet_into_address` varchar(255) DEFAULT NULL COMMENT '钱包转入地址',
  `wallet_credit` varchar(255) DEFAULT NULL COMMENT '钱包金额',
  `wallet_transfer` float DEFAULT NULL COMMENT '钱包转账费用',
  `wallet_balance` varchar(255) DEFAULT NULL COMMENT '钱包余额',
  `deal_Note` varchar(255) DEFAULT NULL COMMENT '交易备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

/*
 * 下面是关于首页任务组的表
 */

CREATE TABLE `work_group_local` (
  `id` int(11) AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

CREATE TABLE `work_group_local_detail` (
  `app_id` int(11) AUTO_INCREMENT,
  `info` varchar(255) NOT NULL COMMENT '任务信息',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '任务名',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '任务类型',
  `status` varchar(255) NOT NULL DEFAULT '' COMMENT '任务状态',
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `graph` text NOT NULL COMMENT '任务规则详细',
  `type_sub` varchar(255) NOT NULL DEFAULT '' COMMENT '任务提交类型',
  `count_local` int(11) NOT NULL  COMMENT '任务抓取本地条数',
  `collection_id` int(11) NOT NULL  COMMENT '任务组id',
  `rule` varchar(255) NOT NULL DEFAULT '' COMMENT '任务规则',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

/*
 * 下面是关于dapp与数据库之间的表
 */

CREATE TABLE `dapp` (
  `id` int(11) AUTO_INCREMENT,
  `dapp_name` varchar(255) DEFAULT NULL,
  `dapp_intro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

CREATE TABLE `dapp_database` (
  `id` int(11) AUTO_INCREMENT,
  `database_name` varchar(255) DEFAULT NULL,
  `wallet_address` varchar(255) DEFAULT NULL,
  `database_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

CREATE TABLE `database_table` (
  `id` int(11) AUTO_INCREMENT,
  `database_id` int(11) DEFAULT NULL,
  `database_table_name` varchar(255) DEFAULT NULL,
  `relevant_dapp_id` varchar(255) DEFAULT NULL,
   `is_delete` int(11) NOT null DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;
