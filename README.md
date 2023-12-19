# Function_Painter_Interpreter
西电软工编译原理上机

这个实现还是比较不错的，易读、简洁，也恰好完成了老师的所有要求。不过代码设计略有瑕疵。

后来重构的Rust版本指路：[https://github.com/Ayana-chan/function_painter_interpreter_rust](https://github.com/Ayana-chan/function_painter_interpreter_rust)

Rust版本里面有非常详细的文档，写有完整的文法。Rust版本更加高效、安全、高封装、高内聚，功能和架构质量都有所提升。Rust版本的功能比Java版本更多更强，特别是变量定义这块儿，超出老师要求，请仔细斟酌使用哪个版本。个人建议抄、学都使用java版本，追求高质量则使用rust版。

>比较讽刺的是，rust版本实现的设计更符合java的理想设计。

# 程序相关

若测试用例运行报规整的Exception，则大概率报的是程序自定义的异常机制，因为我故意写错测试用例来测试这种详尽的、强大的自定义报错。

若报Pair相关错误，则可能是jdk内没有javafx包。一般下载的非简洁版jdk里面大概率都会有，openjdk似乎没有。可以下载javafx包，或者修改程序以舍弃Pair（可以自定义Pair类）。
