package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {

    /*
        SpringConfig에 직접 Bean을 등록할 경우 Config의 timeTraceAop() 메서드도 AOP로 처리하게
        되는데 이 메서드는 자기 자신(TimeTraceAop)를 생성하는 코드로 순환 참조가 발생함
        -> 컴포넌트 스캔 방식을 사용하거나(@Component로 TimeTraceAop를 등록)
        -> AOP의 대상에서 SpringConfig를 제외해주면 해결된다.
     */
    @Around("execution(* hello.hellospring..*.*(..)) && !target(hello.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        /* 어떤 메소드를 호출하는지 출력 */
        System.out.println("START: " + joinPoint.toString());

        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("FINISH: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
