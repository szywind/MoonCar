# Moon Car Simulation
---
### Simulate the dynamics of the system consisted of one remote controller and several cars.
- Each moon car should rotate to some degree according to the commands received from the controller after discovering some obstacles ahead. The reaction time delay is 2s.

- Display the state of the whole system every 500ms. (eg. Including the current position, speed, direction and the predicted position in the next second of all moon cars.)


paths.json 格式说明：

    - "ncar"：车辆数目 (number of moon car)
    
    - "steps": 模拟步数（1步/s）(simulation steps per second)
    
    - "path": 每辆车的路径参数 (path info for each moon car)
    
        - "id": 序号 (identifier)
        
        - "start": 起点坐标 (start position)
        
        - "end": 终点坐标 (end position)
        
        - "d_velocity": 每一步的速度增量 (velocity increment per step)
        
        - "d_angle": 每一步的角度增量 (angular increment per step)
        
