create table member1(
memberid varchar(50) primary key,
name varchar(50) not null,
password varchar(10) not null,
regdate datetime not null
)DEFAULT CHARSET=utf8;

select * from member1;

create table tip_article(
	article_no int auto_increment primary key,
	title varchar(255) not null
);

create table tip_article_content(
	article_no int primary key,
	content text
);

select * from say_article;

select * from say_article_content

select last_insert_id() from say_article;

//limit ���۹�ȣ(0����), size
select * from article order by article_no desc limit 0,2;

select * from article_content where article_no = 7;

select now();










