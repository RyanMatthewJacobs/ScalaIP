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
}



  // lazy val isMulticast: Boolean = {
  //   addrNum match {
  //     case Left(value) => value >= 3758096384L && value <= 4026531839L
  //     case Right(value) =>
  //       value >= BigInt("338953138925153547590470800371487866880") &&
  //         value <= BigInt("340282366920938463463374607431768211455")
  //   }
  // }
  // lazy val isUnspecified: Boolean = {
  //   addrNum match {
  //     case Left(value) => value == 0
  //     case Right(value) => value == 0
  //   }
  // }
  // lazy val isUniqueLocal: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= BigInt("334965454937798799971759379190646833152") &&
  //       value <= BigInt("337623910929368631717566993311207522303")
  //   }
  // }
  // lazy val isIPv4Mapped: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= 281470681743360L &&
  //         value <= 281474976710655L
  //   }
  // }
  // lazy val isIPv4Translated: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= BigInt("18446462598732840960") &&
  //         value <= BigInt("18446462603027808255")
  //   }
  // }
  // lazy val isIPv4IPv6Translated: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= BigInt("524413980667603649783483181312245760") &&
  //         value <= BigInt("524413980667603649783483185607213055")
  //   }
  // }
  // lazy val isTeredo: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= BigInt("42540488161975842760550356425300246528") &&
  //         value <= BigInt("42540488241204005274814694018844196863")
  //   }
  // }
  // lazy val is6to4: Boolean = {
  //   addrNum match {
  //     case Left(_) => false
  //     case Right(value) =>
  //       value >= BigInt("42545680458834377588178886921629466624") &&
  //         value <= BigInt("42550872755692912415807417417958686719")
  //   }
  // }
  // lazy val isReserved: Boolean = {
  //   isUnspecified || isLoopback || isIPv4Mapped || isIPv4Translated || isIPv4IPv6Translated || isTeredo ||
  //     is6to4 || isUniqueLocal || isLinkLocal || isMulticast || isPrivate
  //   addrNum match {
  //     case Left(value) =>
  //       (value >= 0L && value <= 16777215L) ||
  //         (value >= 1681915904L && value <= 1686110207L) ||
  //         (value >= 3221225472L && value <= 3221225727L) ||
  //         (value >= 3221225984L && value <= 3221226239L) ||
  //         (value >= 3227017984L && value <= 3227018239L) ||
  //         (value >= 3323068416L && value <= 3323199487L) ||
  //         (value >= 3325256704L && value <= 3325256959L) ||
  //         (value >= 3405803776L && value <= 3405804031L) ||
  //         (value >= 4026531840L && value <= 4294967294L) ||
  //         (value == 4294967295L)
  //     case Right(value) =>
  //       (value >= BigInt("1329227995784915872903807060280344576") && value <= BigInt("1329227995784915891350551133989896191")) ||
  //         (value >= BigInt("42540490697277043217009159418706657280") && value <= BigInt("42540491964927643445238560915409862655")) ||
  //         (value >= BigInt("42540766411282592856903984951653826560") && value <= BigInt("42540766490510755371168322545197776895"))
  //   }
  // }


  // // IPv4 IPv6 interface functions
  // private def IPv4to2IPv6Octets(ip: IPAddress): String =
  //   s"${(ip.addrNum.left.get >> 16 & 0xFFFF).toHexString}:${(ip.addrNum.left.get & 0xFFFF).toHexString}"
  // private def IPv6OctetsToIPv4(octets: String): IPAddress = {
  //   val octet: String = octets.filter(_!=':')
  //   numToIP(Integer.parseInt(octet, 16))
  // }
  // def sixToFour: IPAddress = {
  //   addrNum match {
  //     case Left(_) => IPAddress(s"2002:${IPv4to2IPv6Octets(this)}::")
  //     case Right(_) =>
  //       require(is6to4, "Not a 6to4 address.")
  //       val octet1 = addr.split(':')(1)
  //       val octet2 = addr.split(':')(2)
  //       IPv6OctetsToIPv4(s"$octet1:$octet2")
  //   }
  // }
  // def sixToFour(subnet: String, interfaceID: String): IPAddress = {
  //   addrNum match {
  //     case Left(_) => IPAddress(s"2002:${IPv4to2IPv6Octets(this)}:$subnet:$interfaceID")
  //     case Right(_) => null
  //   }
  // }
  // def IPv4Mapped: IPAddress = {
  //   addrNum match {
  //     case Left(_) => IPAddress(s"::ffff:${IPv4to2IPv6Octets(this)}")
  //     case Right(_) =>
  //       require(isIPv4Mapped, "Not a IPv4 mapped address.")
  //       val expandedIPv6 = expandIPv6Internal(addr)
  //       val octet1 = expandedIPv6(6)
  //       val octet2 = expandedIPv6(7)
  //       IPv6OctetsToIPv4(s"$octet1:$octet2")
  //   }
  // }
  // def teredoServer: IPAddress = {
  //   addrNum match {
  //     case Left(_) => null
  //     case Right(_) =>
  //       require(isTeredo, "Not a teredo address.")
  //       val expandedIPv6 = expandIPv6Internal(addr)
  //       val octet1 = expandedIPv6(2)
  //       val octet2 = expandedIPv6(3)
  //       IPv6OctetsToIPv4(s"$octet1:$octet2")
  //   }
  // }
  // def teredoClient: IPAddress = {
  //   addrNum match {
  //     case Left(_) => null
  //     case Right(_) =>
  //       require(isTeredo, "Not a teredo address.")
  //       val expandedIPv6 = expandIPv6Internal(addr)
  //       val octet1 = expandedIPv6(6)
  //       val octet2 = expandedIPv6(7)
  //       val toV4 = IPv6OctetsToIPv4(s"$octet1:$octet2")
  //       numToIP(4294967295L ^ BigInt(s"${IPv4ToLong(toV4.addr)}").toLong)
  //   }
  // }
  // def teredo: IPAddress = {
  //   addrNum match {
  //     case Left(_) => IPAddress(s"2001:0:${IPv4to2IPv6Octets(this)}::")
  //     case Right(_) => null
  //   }
  // }
  // def teredo(flags: String, udpPort: String, clientIPv4: String): IPAddress = {
  //   addrNum match {
  //     case Left(_) => IPAddress(s"2001:0:${IPv4to2IPv6Octets(this)}:$flags:$udpPort:$clientIPv4")
  //     case Right(_) => null
  //   }
  // }
  // def teredo(flags: String, udpPort: String, clientIPv4: IPAddress): IPAddress = {
  //   def IPv4XorTo2IPv6Octets: String = {
  //     val xord = BigInt(s"${IPv4ToLong(clientIPv4.addr)}") ^ 4294967295L
  //     s"${(xord >> 16).toString(16)}:${(xord & 65535).toString(16)}"
  //   }
  //   addrNum match {
  //     case Left(_) => IPAddress(s"2001:0:${IPv4to2IPv6Octets(this)}:$flags:$udpPort:$IPv4XorTo2IPv6Octets")
  //     case Right(_) => null
  //   }
  // }