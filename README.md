# custom-annotation-method
This repository is mainly focus on getting methods annotated by custom annotation.

## How to Use
1. Firstly, run the application if you just want to see the result.

2. Secondly, invoke the HTTP GET request with url `/show/common/methods` and port `8080`

3. see result, just like this
```json
{
    "com.example.commonmethod.service.impl.AlarmCommonImpl": [
        {
            "className": "com.example.commonmethod.service.impl.AlarmCommonImpl",
            "methodDesc": "alarmCommon",
            "methodName": "alarmCommon",
            "returnType": "void",
            "args": [
                {
                    "paramName": "list",
                    "paramType": "java.util.List<java.lang.String>"
                },
                {
                    "paramName": "list2",
                    "paramType": "java.util.List<java.lang.Object>"
                },
                {
                    "paramName": "str",
                    "paramType": "java.lang.String"
                }
            ]
        }
    ]
}
```

The core method is `CommonAnnotationUtils.getAllCommonMethods()` with specified annotation named `@CommonAnnotation`, which needed to be marked the method you wanted.

