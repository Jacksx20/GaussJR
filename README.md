# GaussJR

金融场景下GaussDB编程综合实践

---------编译是解决"编码 GBK 的不可映射字符"问题

cd src/expt/db/finance/

javac -encoding utf-8 -classpath ../../../ -d . *.java

java -p /d/Desktop/GaussJR/libs/opengauss-jdbc-2.0.0.jar expt.db.finance.launch
