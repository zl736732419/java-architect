package com.zheng.highconcurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author zhenglian
 * @Date 2019/1/18
 */
public class AtomicIntegerFieldUpdaterApp {
    
    
    private static class Animal {
        protected String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    
    private static class Human extends Animal {
        private volatile int studyLevel;
        private AtomicIntegerFieldUpdater<Human> updater = AtomicIntegerFieldUpdater.newUpdater(Human.class, "studyLevel");

        public int getStudyLevel() {
            return studyLevel;
        }

        public void setStudyLevel(int studyLevel) {
            this.studyLevel = studyLevel;
        }

        public AtomicIntegerFieldUpdater<Human> getUpdater() {
            return updater;
        }

        public void setUpdater(AtomicIntegerFieldUpdater<Human> updater) {
            this.updater = updater;
        }

        public void printField(String name) {
            try {
                System.out.println(this.getClass().getField(name));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void instance() {
        Human human = new Human();
        // 判断human对象是否是Animal类的实例
        System.out.println(Animal.class.isInstance(human));
        // Animal是否是Human的父类或者父接口
        System.out.println(Animal.class.isAssignableFrom(Human.class));
    }
    
    @Test
    public void declareField() throws Exception {
        Human human = new Human();
        human.printField("name"); // 父级字段必须是public
        System.out.println(human.getClass().getDeclaredField("studyLevel"));
        System.out.println(human.getClass().getField("name"));
        System.out.println(human.getClass().getDeclaredField("name"));
    }
    
    @Test
    public void get() {
        Human human = new Human();
        int studyLevel = human.getUpdater().get(human);
        System.out.println(studyLevel);
    }
    
}
