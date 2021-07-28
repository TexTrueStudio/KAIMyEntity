# KAIMyEntity
Replace Minecraft entity with MMD model.
本仓库Fork自 [kjkjkAIStudio/KAIMyEntity](https://github.com/kjkjkAIStudio/KAIMyEntity) 原作者已停止更新，原因请看本仓库KAIMyEntityStory/About.md,模组文件请去Releases页面，此模组已有两个重置版。本仓库仅为备份KAIMyEntity模组构建文件等。

## KAIMyEntitySaba
将Github项目 [benikabocha/saba](https://github.com/benikabocha/saba) 魔改成一个以JNI形式Export函数的动态链接库。
依赖头文件：bullet, glm, spdlog, stb
依赖库文件：bullet (已编译在KAIMyEntitySaba\bullet\lib)
KAIMyEntitySaba.dll存储于本仓库KAIMyEntitySabaLib中


## KAIMyEntity_1.12.2 & KAIMyEntity_1.16.5
API: Forge
接入KAIMyEntitySaba.dll，负责Hook Minecraft的渲染器。
如果可以的话，所有的活儿我都想交给C++侧去做。
但是OpenGL会话由Java发起，因此只能写Java的渲染器。
