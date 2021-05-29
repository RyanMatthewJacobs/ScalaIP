[![license](https://img.shields.io/badge/license-Apache_2.0-blue.svg)](https://github.com/jshalaby510/SparkIP/blob/main/LICENSE)

# IPAddress
IPv4/IPv6 manipulation library for Scala.

## Usage
(Our installation stuff)

## License
This project is licensed under the Apache License. Please see [LICENSE](LICENSE) file for more details.

## Tutorial
### IPAddress
```scala
// Create an IPAddress
val ipv4 = IPAddress("192.0.0.0") // IPv4
val ipv6 = IPAddress("2001::") // IPv6

// Get the version
ipv4.version // 4
ipv6.version // 6

// Check the address type
ipv6.teredo // false
ipv4.isTeredo // false
/*
Supports isPrivate, isGlobal, isLinkLocal, isLoopback, 
isMulticast, isUnspecified, isUniqueLocal, isIPv4Mapped, 
isIPv4Translated, isIPv4IPv6Translated, isTeredo,
is6to4, and isReserved
*/

// Get the numerical value
ipv4.addrNum // Left(3221225472)

// Mask the IP
IPAddress("192.2.1.0").mask(16) // IPAddress(192.2.0.0)
IPAddress("192.2.1.0").mask("255.255.0.0") // IPAddress(192.2.0.0)
IPAddress("2001::23").mask(16) // IPAddress(2001:0:0:0:0:0:0:0)

// Compare IPs
ipv4 == ipv6 // false
ipv4 > ipv6 // false
ipv4 < ipv6 // true

// Sort IPs
Seq(ipv4, ipv6, IPAddress("::"), IPAddress("255.0.0.0")).sorted // List(IPAddress(192.0.0.0), IPAddress(255.0.0.0), IPAddress(::), IPAddress(2001::))
```
### IPNetwork
```scala
// Create an IPNetwork
val v4Net = IPNetwork("192.0.0.0/16") // CIDR
val v4Net2 = IPNetwork("192.0.0.0/255.255.0.0") // Dotted decimal
val v4Net3 = IPNetwork("192.0.0.0-255.255.0.0") // Range

val v6Net = IPNetwork("100::/16") // CIDR
val v6Net2 = IPNetwork("100::-2001::") // Range

// Get the version
v4Net.version // 4
v6Net.version // 6

// Get the range

// Get the numerical value (network address and broadcast address)

// Get the network address

// Get the broadcast address

// Compare networks

// Check if 2 networks intersect

// Sort networks

// Check if a network contains an IPAddress

```
### IPSet
```scala
// Create an IPSet
val set1 = IPSet("::", IPAddress("2.0.0.0"), IPNetwork("::/16"), Set("2::", "3::"))
val set2 = IPSet()

// Get the length of the set

// Compare sets

// Add elements

// Remove elements

// Check if the set contains an IPAddress or IPNetwork

// Clear the set

// Show all addresses and networks

// Return all elements

// Check if its empty

// Get the intersection with another set

// Get the union with another set

// Get the diff with another set

```