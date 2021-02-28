import random
import json

ipList = []

for i in range(10000):
    ip = ".".join(map(str, (random.randint(0, 255)
                            for _ in range(4))))
    ipList.append(ip)

with open('read.json', 'w') as outfile:
    json.dump(ipList, outfile, sort_keys=True, indent=4)