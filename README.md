# GaussJR

金融场景下GaussDB编程综合实践

---------编译是解决"编码 GBK 的不可映射字符"问题

javac -encoding utf-8 -classpath ../../../ -d . *.java

java -p GaussJR/lib/opengauss-jdbc-5.0.1.jar expt.db.finance.launch
