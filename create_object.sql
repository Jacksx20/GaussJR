CREATE SCHEMA finance;
CREATE TABLE "finance"."card_asset"(
	"card_num" bpchar(30) NULL,
	"card_money" numeric(20,2) NULL,
	"moneytype" bpchar(30) NULL
);


insert into "finance"."card_asset"("card_num","card_money","moneytype") values
('6222021302020000001           ','1100.00','人民币                     ');


CREATE TABLE "finance"."client"(
	"c_id" int4 NOT NULL,
	"c_name" varchar(100) NOT NULL,
	"c_id_card" bpchar(20) NOT NULL,
	"c_phone" bpchar(20) NOT NULL,
	"c_mail" bpchar(30) NULL,
	CONSTRAINT "client_pkey" PRIMARY KEY ("c_id"),
	CONSTRAINT "17343_17882_1_not_null" CHECK (c_id IS NOT NULL),
	CONSTRAINT "17343_17882_2_not_null" CHECK (c_name IS NOT NULL),
	CONSTRAINT "17343_17882_3_not_null" CHECK (c_id_card IS NOT NULL),
	CONSTRAINT "17343_17882_4_not_null" CHECK (c_phone IS NOT NULL)
);

set search_path to "finance";
CREATE UNIQUE INDEX client_c_mail_key ON finance.client USING btree (c_mail) TABLESPACE pg_default;

set search_path to "finance";
CREATE UNIQUE INDEX client_c_phone_key ON finance.client USING btree (c_phone) TABLESPACE pg_default;

set search_path to "finance";
CREATE UNIQUE INDEX client_c_id_card_key ON finance.client USING btree (c_id_card) TABLESPACE pg_default;


insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('1','左晓婷','340211199301010001  ','18815650001         ','zuoxiaoting@huawei.com        ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('2','虞成刚','340211199301010002  ','18815650002         ','yuchenggang@huawei.com        ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('3','朱长刚','340211199301010003  ','18815650003         ','zhuchanggang@huawei.com       ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('4','任高峰','340211199301010004  ','18815650004         ','rengaofeng@huawei.com         ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('5','安艳芳','340211199301010005  ','18815650005         ','anyanfang@huawei.com          ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('6','滕长丽','340211199301010006  ','18815650006         ','tengchangli@huawei.com        ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('7','傅小芳','340211199301010007  ','18815650007         ','fuxiaofang@huawei.com         ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('8','卞兰娟','340211199301010008  ','18815650008         ','bianlanjuan@huawei.com        ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('9','邵小婷','340211199301010009  ','18815650009         ','shaoxiaoting@huawei.com       ');
insert into "finance"."client"("c_id","c_name","c_id_card","c_phone","c_mail") values
('10','章晓峰','340211199301010010  ','18815650010         ','zhangxiaofeng@huawei.com      ');


CREATE TABLE "finance"."bank_card"(
	"b_number" bpchar(30) NOT NULL,
	"b_type" bpchar(20) NOT NULL,
	"b_client_id" int4 NOT NULL,
	CONSTRAINT "bank_card_pkey" PRIMARY KEY ("b_number"),
	CONSTRAINT "17343_17893_1_not_null" CHECK (b_number IS NOT NULL),
	CONSTRAINT "17343_17893_2_not_null" CHECK (b_type IS NOT NULL),
	CONSTRAINT "17343_17893_3_not_null" CHECK (b_client_id IS NOT NULL)
);


insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000001           ','信用卡           ','1');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000002           ','信用卡           ','2');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000003           ','信用卡           ','3');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000004           ','信用卡           ','4');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000005           ','储蓄卡           ','5');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000006           ','储蓄卡           ','6');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000007           ','储蓄卡           ','7');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000008           ','储蓄卡           ','8');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000009           ','储蓄卡           ','9');
insert into "finance"."bank_card"("b_number","b_type","b_client_id") values
('6222021302020000010           ','储蓄卡           ','10');


CREATE TABLE "finance"."financial_product"(
	"p_id" int4 NOT NULL,
	"p_name" varchar(100) NOT NULL,
	"p_description" varchar(1000) NULL,
	"p_amount" int4 NULL,
	"p_year" int4 NULL,
	CONSTRAINT "financial_product_pkey" PRIMARY KEY ("p_id"),
	CONSTRAINT "17343_17898_1_not_null" CHECK (p_id IS NOT NULL),
	CONSTRAINT "17343_17898_2_not_null" CHECK (p_name IS NOT NULL)
);


insert into "finance"."financial_product"("p_id","p_name","p_description","p_amount","p_year") values
('1','债券','以国 债、金融央行票据企业为主要投资方向的银理财产品。','50000','6');
insert into "finance"."financial_product"("p_id","p_name","p_description","p_amount","p_year") values
('2','信贷资产','一般指银行作为委托人将通过发理财产品募集资金给信公司，成立计划产购买理财品发售银行或第三方信贷资。','50000','6');
insert into "finance"."financial_product"("p_id","p_name","p_description","p_amount","p_year") values
('3','股票','与股票 挂钩的理财产品。目前市场上主要以港股居多','50000','6');
insert into "finance"."financial_product"("p_id","p_name","p_description","p_amount","p_year") values
('4','大宗商品','与 大宗商品期货挂钩的理财产。 目前市场上主要以挂钩黄金、石油农产品的理财居多。','50000','6');

CREATE TABLE "finance"."financial_asset"(
	"a_id" int4 NOT NULL,
	"a_client_id" int4 NOT NULL,
	"a_product_id" int4 NOT NULL,
	"a_type" int4 NOT NULL,
	"a_status" bpchar(20) NULL,
	"a_quantity" int4 NULL,
	"a_income" int4 NULL,
	"a_purchase_time" date NULL,
	CONSTRAINT "financial_asset_pkey" PRIMARY KEY ("a_id"),
	CONSTRAINT "17343_17906_1_not_null" CHECK (a_id IS NOT NULL),
	CONSTRAINT "17343_17906_2_not_null" CHECK (a_client_id IS NOT NULL),
	CONSTRAINT "17343_17906_3_not_null" CHECK (a_product_id IS NOT NULL),
	CONSTRAINT "17343_17906_4_not_null" CHECK (a_type IS NOT NULL)
);


insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('1','1','1','1','可用              ','4','8001','2018-07-01');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('2','2','2','1','可用              ','3','8002','2018-07-02');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('3','3','3','1','可用              ','2','8003','2018-07-03');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('4','4','4','1','冻结              ','1','8004','2018-07-04');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('99','1','4','1','可用              ','1000','0','2022-05-31');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('100','1','3','1','可用              ','2000','0','2022-03-08');
insert into "finance"."financial_asset"("a_id","a_client_id","a_product_id","a_type","a_status","a_quantity","a_income","a_purchase_time") values
('98','1','2','1','可用              ','500','0',null);

analyze finance.card_asset; 
analyze finance.client; 
analyze finance.bank_card; 
analyze finance.financial_product; 
analyze finance.financial_asset; 