-- dequeue.lua
-- 参数：KEYS[1] - 队列名

local result = redis.call('ZRANGE', KEYS[1], 0, 0)
if result[1] then
    redis.call('ZREM', KEYS[1], result[1])
    return result[1]
else
    return nil
end