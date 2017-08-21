package com.shrikanth.com.access_objects;

import com.shrikanth.com.bulletapi.NotificationReceiver;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by shrikanth on 8/20/17.
 */

public class OwnerClass {
    private Map<String, List<AnnotatedMethod>> receiverMethods;
    private Element classElement;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private String originalClassName;
    private static final String SUFFIX = "$$NotificationReceiver";

    public OwnerClass(Filer filer, Element classElement, Types typeUtils, Elements elementUtils) {
        this.filer = filer;
        this.classElement = classElement;
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.originalClassName =  classElement.getSimpleName().toString();
        this.receiverMethods = new HashMap<>();
    }

    public void addMethod(AnnotatedMethod method){
        List<AnnotatedMethod> methods;
        if(!receiverMethods.containsKey(method.getNotificationId())){
            methods = new ArrayList<>();
            receiverMethods.put(method.getNotificationId(), methods);
        }else{
            methods = receiverMethods.get(method.getNotificationId());
        }
        methods.add(method);
    }


    public void writeCode(){
        String className = originalClassName + SUFFIX;

        /*
          Creating a type name so we can generate a parametrized class.
          UserCustomClass$$NotificationReceiver extends NotificationReceiver<UserCustomClass>{}
         */
        TypeName listenerType = ClassName.get(classElement.asType());

        /*
          Getting the class name of notification receiver
         */
        ClassName currentClassType = ClassName.get(NotificationReceiver.class);

        /*
          Generating parametrized name which gives
          UserCustomClass$$NotificationReceiver extends NotificationReceiver<UserCustomClass>
         */
        ParameterizedTypeName superClass = ParameterizedTypeName.get(currentClassType,listenerType);

        /*
          Creating the class with proper class name
         */
        TypeSpec.Builder builder = TypeSpec.classBuilder(className);

        /*
          Adding the extends class
         */
        builder.superclass(superClass);

        /*
          Adding default constructor
         */
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(listenerType, "data")
                .addStatement("super(data)");

        builder.addMethod(constructor.build());

        /*
          A dummy method for now which overrrides the abstract method
         */
        MethodSpec.Builder handleNotification = MethodSpec.methodBuilder("handleNotification")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .addParameter(Object.class, "data");
        handleNotification.beginControlFlow("switch(id)");
        for (String id : receiverMethods.keySet()) {
            handleNotification.addCode("case $S:\n", id);
            
            handleNotification.addStatement("break");
        }
        handleNotification.endControlFlow();

        builder.addMethod(handleNotification.build());

        TypeSpec typeSpec = builder.build();
        PackageElement packageElement = elementUtils.getPackageOf(classElement);

        try {
            JavaFile.builder(packageElement.getQualifiedName().toString(), typeSpec).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
