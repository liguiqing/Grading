DROP TABLE IF EXISTS  `sso_user`;
CREATE TABLE `sso_user` (
  `ssouser_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `sso_account` VARCHAR(16) COMMENT 'SSO 账号' ,
  `sso_password` VARCHAR(32) COMMENT 'SSO 密码',
  `enabled` INT(1) COMMENT '账号有效性' ,
  PRIMARY KEY (`ssouser_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='SSO账户信息';
insert into `sso_user` (`sso_account`,`sso_password`,`enabled`) values('admin','928bfd2577490322a6e19b793691467e',1);

DROP TABLE IF EXISTS  `subject`;
CREATE TABLE `subject` (
  `subject_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(16) COMMENT '科目名称' ,
  `subject_code` int(11) COMMENT '科目内码' ,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='科目定义';

DROP TABLE IF EXISTS  `term_test`;
CREATE TABLE `term_test` (
  `term_test_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `term_test_oid` BIGINT(20) NOT NULL COMMENT '内码，设置规则：yyyymmdd' ,
  `term_test_name` VARCHAR(16)  COMMENT '考试名称',
  `term_test_from` DATETIME DEFAULT NULL COMMENT '考试开始时间' ,
  `term_test_to` DATETIME DEFAULT NULL COMMENT '考试完成时间' ,
  PRIMARY KEY (`term_test_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学期考试定义';

DROP TABLE IF EXISTS  `test`;
CREATE TABLE `test` (
  `test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `test_oid` bigint(20) NOT NULL COMMENT '内码，设置规则：yyyymmdd' ,
  `subject_id` bigint(20) NOT NULL ,
  `test_name` varchar(16)  COMMENT '考试名称',
  `test_from` datetime DEFAULT NULL COMMENT '考试开始时间' ,
  `test_to` datetime DEFAULT NULL COMMENT '考试完成时间' ,
  `test_year` int(4)  COMMENT '考试年度' ,
  `test_month` int(2)  DEFAULT '1' COMMENT '一年中月分数字(1-12)' ,
  `test_week` int(2)  DEFAULT '1' COMMENT '一年中的周数（取值范围1-54）' ,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='试卷信息定义';

DROP TABLE IF EXISTS  `paper_info`;
CREATE TABLE `paper_info` (
  `paper_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_oid` bigint(20) COMMENT '内码，设置规则：test_oid+1位数字(1-9)' ,
  `paper_name` varchar(16) COMMENT '试卷名称',
  `paper_type` varchar(16) COMMENT '试卷类型',
  `full_score` float(5,2) COMMENT '试卷满分',
  `subjectivity_score` float(5,2) COMMENT '试卷主观题满分',
  `objectivity_score` float(5,2) COMMENT '试卷客观题满分',
  PRIMARY KEY (`paper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='试卷信息定义';

DROP TABLE IF EXISTS  `paper_card`;
CREATE TABLE `paper_card` (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_id` bigint(20) COMMENT '关联到paper_info' ,
  `card_seq` int(2) COMMENT '答题卡图片顺序' ,
  `rotate` int(3) DEFAULT '0' COMMENT '答题卡图片旋转角度 0-360' ,
  `path` varchar(255) COMMENT '答题卡存储的相对路径',
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='试卷答题卡原图信息';


DROP TABLE IF EXISTS  `test_used_paper`;
CREATE TABLE `test_used_paper` (
  `tup_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_id` bigint(20) NOT NULL ,
  `test_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT ='考试用卷';

DROP TABLE IF EXISTS  `paper_section_info`;
CREATE TABLE `paper_section_info` (
  `section_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_id` bigint(20) NOT NULL ,
  `section_oid` bigint(20) NOT NULL COMMENT '内码，设置规则：paper_oid+2位数字(01-99)',
  `section_name` varchar(64) COMMENT '在试卷中大题的题干',
  `section_caption` varchar(64) COMMENT '评卷时显示文字',
  `max_pinci` varchar(64) COMMENT '最多评次,也就是最多只能进行的评卷次数',
  `left` int(4) COMMENT '在试卷扫描图片中相对左边位置，最小值0',
  `top` int(4)  COMMENT '在试卷扫描图片中相对上边位置，最小值0',
  `width` int(4)  COMMENT '在试卷扫描图片中相对宽度，最小值0',
  `height` int(4)  COMMENT '在试卷扫描图片中相对高度，最小值0',
  `full_score` float(5,2) COMMENT '大题满分，如20分，则为20.0',
  `maxerror` float(5,2) COMMENT '大题得分允许误差值，最大值不能超过full_score，如5分，则为5.0',
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='试卷大题定义';

DROP TABLE IF EXISTS  `paper_item_info`;
CREATE TABLE `paper_item_info` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `section_id` bigint(20) NOT NULL ,
  `item_oid` bigint(20) NOT NULL COMMENT '内码，设置规则：section_oid+3(001-999)位数字',
  `item_name` varchar(64) COMMENT '在试卷中小题的题干',
  `item_caption` varchar(64) COMMENT '评卷时显示文字',
  `left` int(4) COMMENT '在大题切割图片中相对左边位置，最小值0，最大值不能超过大题的width值',
  `top` int(4)  COMMENT '在大题切割图片中相对上边位置，最小值0，最大值不能超过大题的height值',
  `width` int(4)  COMMENT '在大题切割图片中相对宽度，最小值0，最大值不能超过大题的width值',
  `height` int(4)  COMMENT '在大题切割图片中相对高度，最小值0，最大值不能超过大题的height值',
  `full_score` float(5,2) COMMENT '小题满分，如5分，则为5.0',
  `validscoredot` varchar(255)  COMMENT '小题给分串,以,号分隔，如1,2,3,4,5',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='试卷小题定义';

DROP TABLE IF EXISTS  `properties_dict`;
CREATE TABLE `properties_dict` (
  `prodict_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prodict_catagory` varchar(32) COMMENT '分类标识，如试卷类型为papertype',
  `prodict_code` varchar(32) COMMENT '分类标识代码，如试卷类型 正卷A卷标识为A',
  `prodict_name` varchar(64) COMMENT '分类标识名称，如试卷类型 正卷A卷标识为A卷'  ,
  PRIMARY KEY (`prodict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='属性字典表,保存以键值对定义的业务数据';

DROP TABLE IF EXISTS  `teacher_info`;
CREATE TABLE `teacher_info` (
  `teacher_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_id` bigint(20) DEFAULT NULL COMMENT '所评科目',
  `teacher_account` varchar(32) DEFAULT NULL COMMENT '评卷教师账号，与subject_code关系为： teacher_account=subject_code+(001-999)',
  `leader` int(4) DEFAULT NULL COMMENT '组长标识，0--组员，1--小组长',
  `teacher_name` varchar(32) DEFAULT NULL COMMENT '评卷教师姓名',
  `teacher_passord` varchar(32) DEFAULT NULL COMMENT '评卷教师密码,需要加密存储',
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8 COMMENT='评卷教师信息';
DROP TABLE IF EXISTS  `student`;

DROP TABLE IF EXISTS  `grade_task`;
CREATE TABLE `grade_task` (
  `task_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) NOT NULL COMMENT '评卷员',
  `item_id` bigint(20) NOT NULL COMMENT '切割块',
  `task_type` varchar(8) DEFAULT NULL COMMENT '评卷任务类型',
  `task_status` varchar(8) DEFAULT NULL COMMENT '评卷任务状态',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8 COMMENT='评卷任务';

DROP TABLE IF EXISTS  `student`;

DROP TABLE IF EXISTS  `student`;
CREATE TABLE `student` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_number` varchar(32) COMMENT '学籍号',
  `student_name` varchar(32) COMMENT '学生姓名',
  `gender` varchar(4) COMMENT '性别',
  `nation` varchar(16) COMMENT '民族',
  `birthday` datetime COMMENT '出生日期',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生信息';

DROP TABLE IF EXISTS  `examinne`;
CREATE TABLE `examinne` (
  `examinne_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) COMMENT '学生ID',
  `term_test_id` bigint(20) COMMENT '考试ID',
  `school_id` bigint(20) COMMENT '考生所在学校ID',  
  `room_id` bigint(20) COMMENT '考场ID',
  `seating_number` int(8) COMMENT '座位号',  
  `examinne_name` varchar(32) COMMENT '考生姓名',
  `examinne_uuid` varchar(32) COMMENT '考生在本次考试中的唯一编码，可以是准考证号，也可以是学号等',
  `uuid_type` varchar(4) COMMENT '01-身份证，学籍号-90，准考证号-91，其他参考国标',
  `arts` int(2) DEFAULT '0' COMMENT '文理科标志；0-不分；1-文科；2-理科 ',
  `clazz_name` varchar(16) COMMENT '班级名称',
  `clazz_code` varchar(16) COMMENT '班级代码',
  `absence` int(2)  DEFAULT '1' COMMENT '缺考标志0-缺考；1-正常',
  `total_score` float(5,2) COMMENT '总分',
  PRIMARY KEY (`examinne_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='考生信息';

DROP TABLE IF EXISTS  `room`;
CREATE TABLE `room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int(16) COMMENT '',
  `room_number` int(8) COMMENT '考场编号',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='考场信息';

DROP TABLE IF EXISTS  `student_do_test`;
CREATE TABLE `student_do_test` (
  `sdt_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `test_id` bigint(20) NOT NULL COMMENT '科目考试ID',
  `examinne_id` bigint(20) NOT NULL COMMENT '考生ID',
  `absence` int(2)  DEFAULT '1' COMMENT '缺考标志0-缺考；1-正常',
  PRIMARY KEY (`sdt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生参加科目考试';

DROP TABLE IF EXISTS  `school`;
CREATE TABLE `school` (
  `school_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `district_id` bigint(20) COMMENT '',
  `school_name` varchar(16) COMMENT '学校名称',
  `school_code` varchar(16) COMMENT '学校代码',
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学校信息';

DROP TABLE IF EXISTS  `district`;
CREATE TABLE `district` (
  `district_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int(16) COMMENT '上级，NULL为最高级',
  `district_number` varchar(32) COMMENT '行政区编号，建议采用国家标准代码',
  `district_name` varchar(64) COMMENT '行政区名称',
  PRIMARY KEY (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='行政区信息，省市区县';


/**************原雷工设计****************/
DROP TABLE IF EXISTS `paperiteminfo`;
CREATE TABLE `paperiteminfo` (
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `itemname` varchar(64) DEFAULT NULL  COMMENT '评卷时显示文字',
  `itemcaption` varchar(64) DEFAULT NULL  COMMENT '评卷时显示文字',
  `fullscore` float(5,2) DEFAULT NULL  COMMENT '大题满分，如20分，则为20.0',
  `maxerror` float(5,2) DEFAULT NULL  COMMENT '大题得分允许误差值，最大值不能超过full_score，如5分，则为5.0',
  `pingci` int(11) DEFAULT NULL COMMENT '必须进行的评卷次数',
  `left` int(4) DEFAULT NULL  COMMENT '在试卷扫描图片中相对左边位置，最小值0',
  `top` int(4)  DEFAULT NULL  COMMENT '在试卷扫描图片中相对上边位置，最小值0',
  `width` int(4) DEFAULT NULL  COMMENT '在试卷扫描图片中相对宽度，最小值0',
  `height` int(4) DEFAULT NULL  COMMENT '在试卷扫描图片中相对高度，最小值0',
  `right` int(4) DEFAULT NULL,
  `answerCardImageIdx` int(11) DEFAULT '1' COMMENT '切割切所在答题卡图片序号',
  `scoreserverip` varchar(30) DEFAULT NULL,
  `scoreserverdbname` varchar(30) DEFAULT NULL,
  `scoreserverdbusername` varchar(30) DEFAULT NULL,
  `scoreserverdbusercode` varchar(100) DEFAULT NULL,
  `keypaperscanpath` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='试卷图片切割定义表，每个切割块定义一条记录';


DROP TABLE IF EXISTS `papersubiteminfo`;
CREATE TABLE `papersubiteminfo` (
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `subitemid` bigint(11) DEFAULT NULL,
  `subitemname` varchar(64) COMMENT '评卷时显示文字',
  `fullscore` float(5,2) COMMENT '小题满分，如5分，则为5.0',
  `subitemcaption` varchar(64) DEFAULT NULL  COMMENT '评卷时显示文字',
  `validscoredot` varchar(255) DEFAULT NULL   COMMENT '小题给分串,以,号分隔，如1,2,3,4,5',
  `defaultviewtype` int(11) DEFAULT NULL,
  `left` int(4) DEFAULT NULL  COMMENT '在切割块中相对左边位置，最小值0',
  `top` int(4)  DEFAULT NULL  COMMENT '在切割块相对上边位置，最小值0',
  `width` int(4) DEFAULT NULL   COMMENT '在切割块相对宽度，最小值0',
  `height` int(4) DEFAULT NULL COMMENT'在切割块相对高度，最小值0',
  `right` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `paperimport`;
CREATE TABLE `paperimport` (
  `testid` bigint(20) DEFAULT NULL COMMENT '科目考试id，关联到test.test_id',
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `kemuoid` bigint(20) DEFAULT NULL COMMENT '考试用卷对应科目id，关联到sbuject.subject_id',
  `studentoid` varchar(32) DEFAULT NULL COMMENT '切割图片所属学生oid,关联到examinee.examinne_uuid',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `diquId` bigint(20) DEFAULT NULL COMMENT '关联到district.district_id',
  `roomid` bigint(20) DEFAULT NULL COMMENT '考场id,关联到room.room_id',
  `virtualroomid` bigint(20) DEFAULT NULL COMMENT '考场id,关联到room.room_id',
  `roomtype` int(11) DEFAULT NULL,
  `pingci` int(11) DEFAULT NULL COMMENT '必须进行的评卷次数，值来源于paperiteminfo.pingci',
  `getmark` int(11) DEFAULT NULL,
  `imagepath` varbinary(255) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='切割图导入信息表，每张切割图片一条记录';

DROP TABLE IF EXISTS `getpaper`;
CREATE TABLE `getpaper` (
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `kemuoid` bigint(20) DEFAULT NULL COMMENT '考试用卷对应科目id，关联到sbuject.subject_id',
  `virtualroomid` bigint(20) DEFAULT NULL COMMENT '考场id,关联到room.room_id',
  `studentoid` varchar(32) DEFAULT NULL COMMENT '切割图片所属学生oid,关联到examinee.examinne_uuid',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `teacheroid` bigint(20) DEFAULT NULL COMMENT '评卷老师id',
  `getpaperdatetime` datetime DEFAULT NULL COMMENT '取卷时间,精确到秒',
  `scored` int(11) DEFAULT '0'  COMMENT '是否已经给分，已取未评：0，已取已评：1',
  `pingci` int(11) DEFAULT NULL COMMENT '当前评次',
  `papertype` varchar(10) DEFAULT NULL COMMENT '试卷类型',
  `imagepath` varchar(255) DEFAULT NULL COMMENT '切割块图片相对路径'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='取卷信息表，每取出一张切割图片，需要在此记录';

DROP TABLE IF EXISTS `scoreinfolog`;
CREATE TABLE `scoreinfolog` (
  `id` bigint(20) DEFAULT NULL COMMENT '流水号',
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `kemuoid` bigint(20) DEFAULT NULL COMMENT '考试用卷对应科目id，关联到sbuject.subject_id',
  `virtualroomid` bigint(20) DEFAULT NULL COMMENT '考场id,关联到room.room_id',
  `studentoid` varchar(32) DEFAULT NULL COMMENT '切割图片所属学生oid,关联到examinee.examinne_uuid',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `papertype` varchar(10) DEFAULT NULL COMMENT '试卷类型，其值来源于getpaper.papertype',
  `score` float(5,2) DEFAULT NULL COMMENT '得分',
  `postdatetime` datetime DEFAULT NULL COMMENT '提交时间',
  `teacheroid` bigint(20) DEFAULT NULL COMMENT '评卷员id,关联到teacher_info.teacher_id',
  `scorestr` varchar(100) DEFAULT NULL COMMENT '评卷员提交的给分串',
  `pingci` int(11) DEFAULT NULL COMMENT '当前进行的评卷次数',
  `spenttime` int(11) DEFAULT NULL COMMENT '花费时间，计算方法是：提交时间postdatetime-getpaper.getpaperdatetime',
  `teacherip` varchar(30) DEFAULT NULL COMMENT '评卷人员计算机的IP地址',
  `memo` varchar(100) DEFAULT NULL,
  `markstr` varchar(100) DEFAULT NULL COMMENT '评卷标识，正常：Y 异常：E 空白 ：B',
  `delmark` int(11) DEFAULT NULL COMMENT '作废标识，Y值为作废，其他值正常',
  `teachermark` varchar(10) DEFAULT NULL COMMENT ''
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='评卷记录表，记录每张切割的评卷情况';

DROP TABLE IF EXISTS `lastscore`;
CREATE TABLE `lastscore` (
  `paperid` bigint(20) DEFAULT NULL COMMENT '考试用卷id，关联到paper_info.paper_id',
  `kemuoid` bigint(20) DEFAULT NULL COMMENT '考试用卷对应科目id，关联到sbuject.subject_id',
  `virtualroomid` bigint(20) DEFAULT NULL COMMENT '考场id,关联到room.room_id',
  `studentoid` varchar(32) DEFAULT NULL COMMENT '切割图片所属学生oid,关联到examinee.examinne_uuid',
  `itemid` bigint(20) DEFAULT NULL COMMENT '切割块id',
  `score` float(5,2) DEFAULT NULL COMMENT '切割块得分，n评计算的最终结果',
  `postdatetime` datetime DEFAULT NULL COMMENT '提交时间',
  `scoretype` int(11) DEFAULT NULL COMMENT '最终得分时的评次',
  `delmark` int(11) DEFAULT NULL COMMENT '删除标记:-1为删除,大于0正常'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='最终成绩信息表，每个切割图片完成评卷后需要在此记录关信息';

