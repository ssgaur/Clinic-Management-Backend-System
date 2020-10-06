package com.ectosense.nightowl.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a test controller used only in tests
 */
@RestController
@RequestMapping("/")
public class MockController
{

    /**
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> test(
            @RequestParam(name = "type", required = false) Integer type
    ) throws Exception
    {
        Map<String,Object> ret = new HashMap<>();
        if (type == null)
        {
            ret.put("test", "Test");
        }
        else
        {
            ret.put("test", "Test-" + type.toString());
        }
        return ret;
    }

}
