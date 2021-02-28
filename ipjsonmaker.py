import random

f = open("ipFile.json", "w")

for i in range(10000):
    ip = ".".join(map(str, (random.randint(0, 255)
                            for _ in range(4))))
    f.write(f"{{\"IPAddress\": \"{ip}\"}}\n")
