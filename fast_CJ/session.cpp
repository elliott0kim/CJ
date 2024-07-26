#include "session.hpp"
#include "define_utility.h"
#include "handler_utility.h"
#include "token_parser.hpp"

session::session(tcp::socket socket)
: stream_(std::move(socket))
{
}

session::~session()
{
}

void session::start()
{
    do_read();
}

void session::do_read()
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

void session::handle_request()
{
    std::string path = std::string(request_.target());
    auto handler = handlers.find(path);
    if (handler != handlers.end())
    {
        // Found the handler for the path
        std::string iss = token_parser::parse_iss(request_);
        PRINT(iss);
        handler->second(stream_, request_);
    }
    else
    {
        // No handler found for the path
        handle_not_found(stream_, request_);
    }

    // After handling request, start reading the next request
    do_read();
}
