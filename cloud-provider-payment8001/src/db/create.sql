create table `payment` (
    `id` bigint unsigned not null auto_increment comment '自增主键id',
    `serial` varchar(200) default '' comment '序列号',
    primary key(`id`)
) engine = InnoDB AUTO_INCREMENT = 1 DEFAULT charset = utf8