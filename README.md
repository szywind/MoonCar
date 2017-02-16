# MoonCar
---
### Simulate the operations of one controller and several cars simoutaneously and manage the event-driven communications between them.


paths.json 格式说明：

    - "ncar"：车辆数目
    
    - "steps": 模拟步数（1步/s）
    
    - "path": 每辆车的路径参数
    
        - "id": 序号
        
        - "start": 起点坐标
        
        - "end": 终点坐标
        
        - "d_velocity": 每一步的速度增量
        
        - "d_angle": 每一步的角度增量
        
