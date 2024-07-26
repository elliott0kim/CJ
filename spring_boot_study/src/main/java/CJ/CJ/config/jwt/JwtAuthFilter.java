//package CJ.CJ.config.jwt;
//
//import CJ.CJ.config.SecurityConfig;
//import CJ.CJ.dto.client.MessageCodeAndResDto;
//import CJ.CJ.service.client.AuthService;
//import CJ.CJ.config.jwt.JwtUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//public class JwtAuthFilter implements Filter {
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    @Autowired
//    AuthService authService;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // 초기화 코드 (필요한 경우)
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
//            throws IOException, ServletException {
//        if (!(servletRequest instanceof HttpServletRequest)) {
//            throw new ServletException("Non HTTP request received.");
//        }
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String authorizationHeader = request.getHeader("Authorization");
//
//        response.setContentType("applcation/json");
//        response.setCharacterEncoding("UTF-8");
//        MessageCodeAndResDto messageCodeAndResDto = new MessageCodeAndResDto();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonRes;
//
//        //JWT가 헤더에 있는 경우
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
//        {
//            String token = authorizationHeader.substring(7);
//            //JWT 유효성 검증
//            jwtUtil.validateToken(token);
//
//            String userId = jwtUtil.getUserId(token);
//            authService.checkExistUserById(userId);
//        }
//        else
//        {
//            String requestURI = request.getRequestURI();
//
//            if (!request.getRequestURI().matches("^/register/.*")
//                    && !request.getRequestURI().matches("^/login.*")
//                    && !request.getRequestURI().matches("^/duplication.*")
//                    && !request.getRequestURI().matches("^/api.*")
//                    && !request.getRequestURI().matches("^/swagger.*")
//                    && !request.getRequestURI().matches("/v3.*")
//                    && !request.getRequestURI().matches("^/api.*"))
//            {
//                throw new ServletException("need token");
//            }
//        }
//
//        chain.doFilter((ServletRequest) request, (ServletResponse) response);
//    }
//
//    @Override
//    public void destroy() {
//        // 종료 코드 (필요한 경우)
//    }
//}
//
//
//
//
////        else
////        {
////            String a = request.getRequestURI();
////
////            if (!request.getRequestURI().matches("^/auth/.*")
////                    && !request.getRequestURI().matches("^/swagger.*")
////                    && !request.getRequestURI().matches("/v3.*")
////                    && !request.getRequestURI().matches("^/api.*"))
////            {
////                return;
////            }
////        }
//
////filterChain.doFilter(request, response); // 다음 필터로 넘기기
//
////    @Override
////    /**
////     * JWT 토큰 검증 필터 수행
////     */
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
////    {
////
////    }
