// MyClass.hpp
#ifndef TOKEN_PARSER_HPP
#define TOKEN_PARSER_HPP

#include <iostream>
#include <string>
#include "boost_header.h"

using namespace std;

class token_parser
{
public:
    token_parser();
    ~token_parser();
    static string parse_iss(http::request<http::string_body>& req);
};

#endif // TOKEN_PARSER_HPP
