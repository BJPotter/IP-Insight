-- enqueue.lua
-- 参数：KEYS[1] - 队列名
-- 参数：ARGV[1] - 元素
-- 参数：ARGV[2] - 优先级

redis.call('ZADD', KEYS[1], ARGV[2], ARGV[1])
return 1