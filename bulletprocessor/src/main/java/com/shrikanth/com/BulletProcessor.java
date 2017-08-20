package com.shrikanth.com;

import com.google.auto.service.AutoService;
import com.shrikanth.com.access_objects.OwnerClass;
import com.shrikanth.com.bulletapi.Subscribe;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@SupportedAnnotationTypes("com.shrikanth.com.bulletapi.Subscribe")
@AutoService(Processor.class)
public class BulletProcessor extends AbstractProcessor{

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(Subscribe.class)){
            //validateElement(element);
            //AnnotatedField annotatedField = new AnnotatedField(element);
            Element ownerElement = element.getEnclosingElement();
            //OwnerClass ownerClass = ownerClassMap.get(element.getEnclosingElement());
            //if(ownerClass == null){
            OwnerClass ownerClass = new OwnerClass(filer, ownerElement, typeUtils, elementUtils);
              //  ownerClassMap.put(ownerElement, ownerClass);
            //}
            ownerClass.writeCode();
        }
       /* Iterator<Element> iterator = ownerClassMap.keySet().iterator();
        while (iterator.hasNext()){
            Element ownerElement = iterator.next();
            OwnerClass ownerClass = ownerClassMap.get(ownerElement);
            ownerClass.generateCode();
        }
        ownerClassMap.clear();*/
        return true;
    }
}
