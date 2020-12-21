# Java2020_DNA_Protein_Judge
Work for Java Course, Beijing Institute of Technology, 2020, BinLiu
# To Check Complete Version of this Development Doc, please download the PDF File in the root of master repository.
# 要查看带完整开发文档，请下载根目录下的PDF文件

## 面向真实科学问题的 Java 课程设计

#### 1. 项目简介

 简单地说，本项目要实现的最终目标是，判断某蛋白质序列是否是DNA结合蛋白。报告给出的具体实现已经实现的结果是，将给定的蛋白质序列FASTA格式（序列名/注释+单个字母表示的核酸或氨基酸）输入，转换成一串便于分类器理解的向量。向量间数学特征的差异，可以反映不同序列输入的文字特征和语义特征，有助于区分不同的蛋白质序列。

项目初步实现的功能包括，运用Kmer分词方法创建词典，给出了包括OneHot Encoding、Bag Of Words、TF-IDF在内的三种特征提取方法。用户可以通过有限的图形交互界面来实现基于文件输入的交互，并获得向量序列的文件输出。项目简单实现了数据与表现的分离，提供了一定的可扩展性，既可以通过调用项目实现的有限内容来获得向量序列的文件输出，也可以通过调用项目包含的底层数据逻辑来构造进一步的机器学习模型。
#### 2. 实现方法与细节

注：benchmark_set样本集需要用户拆分成两个文件，即DNA结合蛋白集
与非DNA结合蛋白集，前者本文称之为正样本(集)，相应后者就是负样本
(集)，independent_set则称之为独立样本(集)。

详见PDF文档
#### 3. 实验结果与分析
详见PDF文档

#### 附录：开发文档

1. 开发环境

```
JavaSE-1.8 (Amazon Corretto 8 [1.8.0_275])
```
2. 开发平台
```
    Eclipse IDE for Java Developers (includes Incubating components)
       Version: 2020-09 (4.17.0) Build id: 20200910- 1200
```
3. 项目内容
```
    可执行程序：DNA-Protein.jar
    数据集示例文件：data_pos.txt data_neg.txt data_i.txt
    数据集在Kmer= 2 时的TFIDF示例输出：Example_TFIDF_Kmer2.zip
    程序源代码（包含java-doc）：DNA-Protein
```
4. 程序调用方法
```
    1) 安装版本不低于JRE 8 （推荐）的Java运行环境
    2) 将程序jar文件（DNA-Protein）复制到计算机上，准备好样本数据集文
       件，或者使用程序给出的示例文件
    3) 如果要同时调用命令行界面和图形界面，则
       在项目所在目录进入cmd或powershell或terminal界面，通过java -jar
       DNA-protein.jar来运行程序。
    4) 如果只希望调用图形界面，则在设置好文件关联的前提下直接打开DNA-
       Protein.jar文件，或者选择以Java Runtime Environment打开。
    5) 对于命令行界面的调用
       a) 输入任意字符回车，进入程序流程
       b) 程序会要求指定样本数据集路径，通过拖拽文件或者直接输入文件
          名的方式，依次指定正样本、负样本、独立样本文件。
       c) 之后程序会要求指定Kmer维度，这是为了建立词典，建议输入不大
          于 4 的正整数
       d) 此后程序给出编码方式选择，提供O – OneHotEncoding编码，B
          - BagOfWords编码，T – TF-IDF编码三种方式可供选择
       e) 编码完毕，会询问你是否需要将其保存到文件，输入Y会将向量化
          编码结果保存到当前目录。
     6) 对于图形界面的调用
        a) 点击菜单：文件-打开正数据集，选择正数据集文件
        b) 点击菜单：文件-打开负数据集，打开负数据集文件
        c) 点击菜单：文件-打开独立数据集，打开独立数据集文件
        d) 点击运行 – 选择一种编码方式，如果之前操作都正确，会弹出输
        入窗口
        e) 在新弹出的窗口中输入Kmer维度（建议输入不大于 4 的正整数），
        点击确定
        f) 程序将进行处理，并在程序日志区域显示运行时间，Kmer维度较大
        或样本集较大时，处理速度可能较慢，请耐心等候。
        g) 点击菜单：文件 – 保存全部数据到当前文件夹，程序会自动保存
        运行结果到可执行jar包所在的文件夹，选择其他保存选项可以保
        存部分结果。
        h) 你可以选择其他文件或者更改向量化编码运行方式来执行程序和保
        存文件。
```

