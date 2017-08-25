package com.shrikanth.com.access_objects;


import com.shrikanth.com.bulletapi.Subscribe;
import com.squareup.javapoet.CodeBlock;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

/**
 * Created by shrikanth on 8/20/17.
 */

public class AnnotatedMethod {

    Element element;
    ExecutableType executableType;
    String methodName;
    Subscribe annotation;
    //String[] params;
    List<? extends TypeMirror> parameterTypes;

    public AnnotatedMethod(Element element) {
        if(element.asType() instanceof ExecutableType) {
            this.element = element;
            this.executableType = (ExecutableType)element.asType();
            this.parameterTypes = executableType.getParameterTypes();
            this.methodName = element.getSimpleName().toString();
            annotation = element.getAnnotation(Subscribe.class);
        }
    }


    public String getNotificationId() {
        return annotation.id();
    }

    public boolean isSticky(){
        return annotation.sticky();
    }

    public String getMethodName() {
        return methodName;
    }

    public String getCode(){
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        if(parameterTypes != null && parameterTypes.size() > 0) {
            TypeMirror parameter  = parameterTypes.get(0);
            if(parameter.getKind().isPrimitive()) {
                codeBlock.beginControlFlow("if($L != null)", "data");
            }else{
                codeBlock.beginControlFlow("if($L instanceof $L \n || $L == null)", "data", parameter.toString(), "data");
            }
            codeBlock.addStatement("listener.$L(($L)$L)", methodName, parameterTypes.get(0).toString(), "data");
            codeBlock.endControlFlow();
        }else{
            codeBlock.addStatement("listener.$L()", methodName);
        }

        return codeBlock.build().toString();
    }
}
