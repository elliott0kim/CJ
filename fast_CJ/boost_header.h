#ifndef BOOST_HEADER_H
#define BOOST_HEADER_H

#include <boost/beast/core.hpp>
#include <boost/beast/http.hpp>
#include <boost/asio/ip/tcp.hpp>
#include <boost/asio/io_context.hpp>
#include <boost/beast/version.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>

namespace beast = boost::beast;           // from <boost/beast.hpp>
namespace http = boost::beast::http;      // from <boost/beast/http.hpp>
namespace net = boost::asio;              // from <boost/asio.hpp>
using tcp = boost::asio::ip::tcp;         // from <boost/asio/ip/tcp.hpp>

#endif // BOOST_HEADER_H
