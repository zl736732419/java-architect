package com.zheng.highconcurrent.nonlock;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author zhenglian
 * @Date 2019/1/10
 */
public class AtomicReferenceApp {
    
    private static AtomicReference<Person> person = new AtomicReference<>(new Person("zhangsan"));
    
    private static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }
            if (!(obj instanceof Person)) {
                return false;
            }
            Person other = (Person) obj;
            return Objects.equals(other.getName(), this.getName());
        }
    }
    
    public static void main(String[] args) {
//        System.out.println(new Person("zhangsan").equals(person.get()));
//        Person expact = new Person("zhangsan");
        // AtomicReference 比较引用采用的是==，而不是equals，所以被期望的引用必须是get()的同一个引用
        Person expact = person.get();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean result = person.compareAndSet(expact, new Person("lisi"));
                    if (result) {
                        System.out.println(Thread.currentThread() + " update success");
                    } else {
                        System.out.println(Thread.currentThread() + " update failed");
                    }
                }
            }).start();
        }
    }
    
}
