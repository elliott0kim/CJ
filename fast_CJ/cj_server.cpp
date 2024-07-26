#include <iostream>
#include <string>
#include <unordered_map>
#include <functional>
#include <memory>
#include <thread>
#include <sstream>
#include <iomanip>
#include <chrono>

#include "define_utility.h"
#include "boost_header.h"
//#include "session.hpp"
#include "token_parser.hpp"

struct heart_rate_info
{
    std::string id;
    std::string work_date; // yyyy-mm-dd

    bool operator==(const heart_rate_info& other) const
    {
        if (id == other.id && work_date == other.work_date)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
};

// std::hash 특수화
namespace std {
    template <>
    struct hash<heart_rate_info> {
        size_t operator()(const heart_rate_info& info) const {
            // std::hash<string>를 사용하여 id와 work_date의 해시 값을 계산
            size_t h1 = hash<std::string>()(info.id);
            size_t h2 = hash<std::string>()(info.work_date);
            
            // 두 해시 값을 결합하여 반환
            // 참고: 해시 값을 결합하는 방법은 여러 가지가 있지만, 다음은 간단한 예시입니다.
            return h1 ^ (h2 << 1); // h2를 왼쪽으로 시프트하여 결합
        }
    };
}


std::unordered_map<heart_rate_info, std::vector<unsigned char>> total_heart_rate_info;

// Handler for the /main endpoint
void handle_get_heart_rate(beast::tcp_stream& stream, http::request<http::string_body>& req, std::string id)
{
    try
    {
        auto now = std::chrono::system_clock::now();
        // 현재 시간을 std::time_t로 변환합니다.
        std::time_t now_time_t = std::chrono::system_clock::to_time_t(now);
        // std::tm 구조체로 변환합니다.
        std::tm now_tm = *std::localtime(&now_time_t);    
        // 연도, 월, 일을 추출하여 변수에 저장합니다.
        std::ostringstream today;
        today << std::put_time(&now_tm, "%Y-%m-%d");
        std::string formatted_date = today.str();
        
        heart_rate_info heart_rate_info_s;
        heart_rate_info_s.id = id;
        heart_rate_info_s.work_date = today.str();

        std::ostringstream data;
        auto it = total_heart_rate_info.find(heart_rate_info_s);
        if (it != total_heart_rate_info.end())
        {
            std::vector<unsigned char>* heart_rate_arr = &it->second;
            data << "[";
            for (size_t i = 0; i < heart_rate_arr->size(); ++i) {
                if (i > 0) {
                    data << ", ";  // 요소 사이에 쉼표와 공백 추가
                }
                unsigned char temp_data = (*heart_rate_arr)[i];
                data << static_cast<int>(temp_data);  // unsigned char를 int로 변환하여 출력
        }

        data << "]";
        }
        else
        {
            data << "NULL";
        }

    // total_heart_rate_info[heart_rate_info_s].push_back();

        http::response<http::string_body> res{http::status::ok, req.version()};
        res.set(http::field::content_type, "application/json");
        std::ostringstream res_body;
        res_body << "{\n";
        res_body << "\"message\" : \"SUCCESS\" , \n";
        res_body << "\"data\" : \n";
        res_body << data.str();
        res_body << "}";
        res.body() = res_body.str();
        res.prepare_payload();
        http::write(stream, res);
    }
    catch (std::exception const& e)
    {
        PRINT(req.body());
        http::response<http::string_body> res{http::status::internal_server_error, req.version()};
        res.set(http::field::content_type, "application/json");
        std::ostringstream res_body;
        res_body << "{\n";
        res_body << "UNKNOWN_ERROR";
        res_body << "}";
        res.body() = res_body.str();
        res.prepare_payload();
        http::write(stream, res);
    }
}

// Handler for the /hello endpoint
void handle_set_heart_rate(beast::tcp_stream& stream, http::request<http::string_body>& req, std::string id)
{
    try
    {
        std::string json_data = req.body();
        std::istringstream json_stream(json_data);

        // Property Tree 객체 생성
        boost::property_tree::ptree pt;

        boost::property_tree::read_json(json_stream, pt);

        // 값 추출
        int heart_rate_now = pt.get<int>("data");

        auto now = std::chrono::system_clock::now();
        // 현재 시간을 std::time_t로 변환합니다.
        std::time_t now_time_t = std::chrono::system_clock::to_time_t(now);
        // std::tm 구조체로 변환합니다.
        std::tm now_tm = *std::localtime(&now_time_t);    
        // 연도, 월, 일을 추출하여 변수에 저장합니다.
        std::ostringstream today;
        today << std::put_time(&now_tm, "%Y-%m-%d");
        std::string formatted_date = today.str();
        heart_rate_info heart_rate_info_s;
        heart_rate_info_s.id = id;
        heart_rate_info_s.work_date = today.str();
        auto it = total_heart_rate_info.find(heart_rate_info_s);
        if (it != total_heart_rate_info.end())
        {
            std::vector<unsigned char>* heart_rate_arr = &it->second;
            heart_rate_arr->push_back(static_cast<unsigned char>(heart_rate_now));
        }
        else
        {
            std::vector<unsigned char> heart_rate_data;
            heart_rate_data.push_back(heart_rate_now);
            total_heart_rate_info[heart_rate_info_s] = heart_rate_data;

            // Create an io_context object to manage asynchronous operations
            net::io_context ioc;
            // Create a resolver to turn the server address into an endpoint
            tcp::resolver resolver(ioc);
            auto const results = resolver.resolve("127.0.0.1", "8080");
            // Create a socket and connect to the resolved endpoint
            tcp::socket socket(ioc);
            net::connect(socket, results.begin(), results.end());
            http::request<http::string_body> req{http::verb::post, "/updateWorkNow", 11};
            req.set(http::field::host, "127.0.0.1");
            req.set(http::field::content_type, "application/json");
            std::ostringstream json_body;
            json_body << "{\n";
            json_body << "\"userId\" : \n";
            json_body << "\"";
            json_body << id;
            json_body << "\"";
            json_body << "\n}";
            req.body() = json_body.str();
            req.prepare_payload();  // Calculates Content-Length and sets appropriate headers

            // Send the request
            http::write(socket, req);
        }
        
        std::ostringstream data;
        data << "NULL";

        http::response<http::string_body> res{http::status::ok, req.version()};
        res.set(http::field::content_type, "application/json");
        std::ostringstream res_body;
        res_body << "{\n";
        res_body << "\"message\" : \"SUCCESS\" , \n";
        res_body << "\"data\" : \n";
        res_body << data.str();
        res_body << "}";
        res.body() = res_body.str();
        res.prepare_payload();
        http::write(stream, res);
    }
    catch (std::exception const& e)
    {
        PRINT(req.body());
        http::response<http::string_body> res{http::status::internal_server_error, req.version()};
        res.set(http::field::content_type, "application/json");
        std::ostringstream res_body;
        res_body << "{\n";
        res_body << "UNKNOWN_ERROR";
        res_body << "}";
        res.body() = res_body.str();
        res.prepare_payload();
        http::write(stream, res);
    }
}


// Typedef for request handler function
using RequestHandler = std::function<void(beast::tcp_stream&, http::request<http::string_body>&, std::string)>;

// Map to associate URIs with handlers
std::unordered_map<std::string, RequestHandler> handlers = {
    {"/getHeartRate", handle_get_heart_rate},
    {"/setHeartRate", handle_set_heart_rate}
};


// Handler for unknown endpoints
void handle_not_found(beast::tcp_stream& stream, http::request<http::string_body>& req)
{
    http::response<http::string_body> res{http::status::not_found, req.version()};
    res.set(http::field::content_type, "application/json");
    std::ostringstream res_body;
    res.body() = res_body.str();
    res.prepare_payload();
    http::write(stream, res);
}

// This function processes the HTTP request
void handle_request(beast::tcp_stream& stream, http::request<http::string_body>&& req)
{
    std::string path = std::string(req.target());
    auto handler = handlers.find(path);
    if (handler != handlers.end())
    {
        // Found the handler for the path
        std::string id = token_parser::parse_iss(req);
        handler->second(stream, req, id);
    }
    else
    {
        // No handler found for the path
        handle_not_found(stream, req);
    }
}

// Asynchronous session handler
class session : public std::enable_shared_from_this<session>
{
public:
    session(tcp::socket socket) : stream_(std::move(socket)) {}

    void start()
    {
        do_read();
    }

private:
    void do_read()
    {
        auto self = shared_from_this();
        http::async_read(stream_, buffer_, request_,
            [self](beast::error_code ec, std::size_t)
            {
                if (!ec)
                {
                    self->handle_request();
                }
            });
    }

    void handle_request()
    {
        std::string path = std::string(request_.target());
        auto handler = handlers.find(path);
        if (handler != handlers.end())
        {
            // Found the handler for the path
            std::string id = token_parser::parse_iss(request_);
            //PRINT(iss);
            handler->second(stream_, request_, id);
        }
        else
        {
            // No handler found for the path
            handle_not_found(stream_, request_);
        }

        // After handling request, start reading the next request
        do_read();
    }

    beast::flat_buffer buffer_;
    http::request<http::string_body> request_;
    beast::tcp_stream stream_;
};

// Function to process connections
void do_session(tcp::socket socket)
{
    std::make_shared<session>(std::move(socket))->start();
}

// Function to asynchronously accept connections
void do_accept(tcp::acceptor& acceptor, net::io_context& ioc)
{
    acceptor.async_accept(
        [&](beast::error_code ec, tcp::socket socket)
        {
            if (!ec)
            {
                std::thread{[s = std::move(socket)]() mutable {
                    do_session(std::move(s));
                }}.detach();
            }

            // Accept the next connection
            do_accept(acceptor, ioc);
        });
}

int main()
{
    try
    {
        net::io_context ioc{4}; // Number of threads in the pool
        tcp::acceptor acceptor{ioc, {tcp::v4(), 8081}};
        // Start accepting connections
        //net::socket_base::reuse_address option(true);
        net::socket_base::reuse_address option(true);
        acceptor.set_option(option);

        do_accept(acceptor, ioc);

        // Run the I/O context to process asynchronous operations
        ioc.run();
    }
    catch (std::exception const& e)
    {
        std::cerr << "Exception: " << e.what() << std::endl;
    }
}
