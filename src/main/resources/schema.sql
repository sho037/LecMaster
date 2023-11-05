/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE student (
    id IDENTITY,
    name VARCHAR
);
