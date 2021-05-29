package com.databricks115
import org.scalatest.FunSuite

class TestIPAddress extends FunSuite {

  test("Constructor") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")

    assert(ip1.addrNum.right.get == 9)
    assert(ip2.addrNum.left.get == 9)
  }

  test("==") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")

    assert(ip1 == ip1)
    assert(ip2 == ip2)
    assert(!(ip1 == ip2))
  }

  test("!=") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")

    assert(ip1 != ip2)
    assert(!(ip1 != ip1))
  }

  test("<") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")
    val ip3 = IPAddress("::10")

    assert(ip2 <  ip1)
    assert(ip1 <  ip3)
  }

  test(">") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")
    val ip3 = IPAddress("::10")

    assert(ip1 >  ip2)
    assert(ip3 >  ip1)
  }

  test(">=") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")
    val ip3 = IPAddress("::10")

    assert(ip1 >=  ip2)
    assert(ip3 >=  ip1)
    assert(ip1 >= ip1)
    assert(ip2 >= ip2)
  }

  test("<=") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")
    val ip3 = IPAddress("::10")

    assert(ip2 <  ip1)
    assert(ip1 <  ip3)
    assert(ip1 <= ip1)
    assert(ip2 <= ip2)
  }

  test("sorted") {
    val ip1 = IPAddress("::9")
    val ip2 = IPAddress("0.0.0.9")
    val ip3 = IPAddress("::10")
    val ipseq = Seq(ip1, ip3, ip2)

    assert(ipseq.sorted == Seq(ip2, ip1, ip3))
  }

  test("mask") {
    val ip1 = IPAddress("2:5::6")
    val ip2 = IPAddress("192.0.5.1")

    assert(ip1.mask(16) == IPAddress("2::"))
    assert(ip2.mask(16) == IPAddress("192.0.0.0"))
    assert(ip2.mask("255.255.0.0") == IPAddress("192.0.0.0"))
  }

  test("compare - equal") {
    val ip1 = IPAddress("2:5::6")
    val ip2 = IPAddress("2:5::6")
    assert(ip1.compare(ip2) == 0)
  }

  test("compare - less than") {
    val ip1 = IPAddress("2:5::6")
    val ip2 = IPAddress("2:6::6")
    assert(ip1.compare(ip2) == -1)
  }

  test("compare - greater than") {
    val ip1 = IPAddress("2:6::6")
    val ip2 = IPAddress("2:5::6")
    assert(ip1.compare(ip2) == 1)
  }

  test("isPrivate - true") {
    val ip = IPAddress("192.168.0.1")
    assert(ip.isPrivate)
  }

  test("isPrivate - false") {
    val ip = IPAddress("7.0.0.1")
    assert(!ip.isPrivate)
  }

  test("isGlobal - false") {
    val ip = IPAddress("192.168.0.1")
    assert(!ip.isGlobal)
  }

  test("isGlobal - true") {
    val ip = IPAddress("7.0.0.1")
    assert(ip.isGlobal)
  }

  test("isLinkLocal - true") {
    val ip = IPAddress("169.254.0.1")
    assert(ip.isLinkLocal)
  }

  test("isLinkLocal - false") {
    val ip = IPAddress("7.0.0.1")
    assert(!ip.isLinkLocal)
  }

  test("isLoopback - true") {
    val ip1 = IPAddress("127.0.0.1")
    val ip2 = IPAddress("::1")
    assert(ip1.isLoopback)
    assert(ip2.isLoopback)
  }

  test("isLoopback - false") {
    val ip1 = IPAddress("128.0.0.5")
    val ip2 = IPAddress("::3")
    assert(!ip1.isLoopback)
    assert(!ip2.isLoopback)
  }

  test("isMulticast - true") {
    val ip1 = IPAddress("224.3.0.1")
    val ip2 = IPAddress("ff00::1")
    assert(ip1.isMulticast)
    assert(ip2.isMulticast)
  }

  test("isMulticast - false") {
    val ip1 = IPAddress("255.0.0.5")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isMulticast)
    assert(!ip2.isMulticast)
  }

  test("isUnspecified - true") {
    val ip1 = IPAddress("0.0.0.0")
    val ip2 = IPAddress("::")
    assert(ip1.isUnspecified)
    assert(ip2.isUnspecified)
  }

  test("isUnspecified - false") {
    val ip1 = IPAddress("255.0.0.5")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isUnspecified)
    assert(!ip2.isUnspecified)
  }

  test("isUniqueLocal - true") {
    val ip = IPAddress("fce0::1:1")
    assert(ip.isUniqueLocal)
  }

  test("isUniqueLocal - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isUniqueLocal)
    assert(!ip2.isUniqueLocal)
  }

  test("isIPv4Mapped - true") {
    val ip = IPAddress("::ffff:c000:0280") // 192.0.2.128
    assert(ip.isIPv4Mapped)
  }

  test("isIPv4Mapped - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isIPv4Mapped)
    assert(!ip2.isIPv4Mapped)
  }

  test("isIPv4IPv6Translated - true") {
    val ip = IPAddress("64:ff9b::c000:201") // 192.0.2.128
    assert(ip.isIPv4IPv6Translated)
  }

  test("isIPv4IPv6Translated - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isIPv4IPv6Translated)
    assert(!ip2.isIPv4IPv6Translated)
  }

  test("isIPv4Translated - true") {
    val ip = IPAddress("::ffff:0:c000:201") // 192.0.2.128
    assert(ip.isIPv4Translated)
  }

  test("isIPv4Translated - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isIPv4Translated)
    assert(!ip2.isIPv4Translated)
  }

  test("isTeredo - true") {
    val ip = IPAddress("2001:0:c000:201::")
    assert(ip.isTeredo)
  }

  test("isTeredo - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isTeredo)
    assert(!ip2.isTeredo)
  }

  test("is6to4 - true") {
    val ip = IPAddress("2002:0:c000:201::")
    assert(ip.is6to4)
  }

  test("is6to4 - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.is6to4)
    assert(!ip2.is6to4)
  }

  test("isReserved - true") {
    val ip = IPAddress("255.0.0.5")
    val ip2 = IPAddress("2001::")
    assert(ip2.isReserved)
    assert(ip.isReserved)
  }

  test("isReserved - false") {
    val ip1 = IPAddress("192.158.0.1")
    val ip2 = IPAddress("f::3")
    assert(!ip1.isReserved)
    assert(!ip2.isReserved)
  }

  test("sixToFour") {
    val ip = IPAddress("192.158.0.1")
    assert(ip.sixToFour == IPAddress("2002:c09e:0001::"))
  }

  test("ipv4Mapped") {
    val ip = IPAddress("192.158.0.1")
    assert(ip.ipv4Mapped == IPAddress("::ffff:c09e:0001"))
  }

  test("teredoServer") {
    val ip = IPAddress("2001:0:c000:201::")
    assert(ip.teredoServer == IPAddress("12.0.2.1"))
  }

   test("teredoClient") {
    val ip = IPAddress("2001:0:c000:201::")
    assert(ip.teredoClient == IPAddress("255.255.255.255"))

     val v4Net = IPNetwork("192.0.0.0/16")
     val v6Net = IPNetwork("100::/16")
  }

  test("teredo") {
    val ip = IPAddress("192.158.0.1")
    assert(ip.teredo == IPAddress("2001:0:c09e:0001::"))
  }
//
//  test("teredo params 1") {
//    val ip = IPAddress("192.158.0.1")
//    val flags = "eb"
//    val udpPort = "cc"
//    val clientIPv4 = "3fff:fdd2"
//    assert(ip.teredo(flags, udpPort, clientIPv4) == IPAddress("2001:0:c09e:0001:eb:cc:3fff:fdd2"))
//  }
//
//  test("teredo params 2") {
//    val ip = IPAddress("192.158.0.1")
//    val flags = "eb"
//    val udpPort = "cc"
//    val clientIPv4 = IPAddress("192.0.2.45")
//    assert(ip.teredo(flags, udpPort, clientIPv4) == IPAddress("2001:0:c09e:0001:eb:cc:3fff:fdd2"))
//  }
}
