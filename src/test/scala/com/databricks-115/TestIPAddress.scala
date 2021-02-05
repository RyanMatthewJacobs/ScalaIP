package com.databricks115

import org.scalatest.FunSuite

class TestIPAddress extends FunSuite with SparkSessionTestWrapper{
 
    test("isIP - valid IP") {
        var ip = IPAddress("192.168.0.1")
        assert(ip.isIP)
    }

    test("isIP - too few bytes") {
        assertThrows[InvalidIPException] {
            var ip = IPAddress("192.168.0")
        }
    }

    test("isIP - no dots") {
        assertThrows[InvalidIPException] {
            var ip = IPAddress("19216800")
        }
    }

    test("isIP - not a number") {
        assertThrows[InvalidIPException] {
            var ip = IPAddress("Hello world!")
        }
    }

    test("toString - valid IP") {
        var ip = IPAddress("192.168.0.1")
        assert(ip.toString === "192.168.0.1")
    }

    test("mask - valid mask 1") {
        var ip = IPAddress("192.168.0.1").mask("255.0.0.0").get
        assert(ip.toString === "192.0.0.0")
    }

    test("mask - valid mask 2") {
        var ip = IPAddress("192.168.0.1").mask("255.255.0.0").get
        assert(ip.toString === "192.168.0.0")
    }

    test("mask - invalid mask") {
        assertThrows[Exception] {
            var ip = IPAddress("192.168.0.1").mask("255.0.0").get
        }
    }
}
